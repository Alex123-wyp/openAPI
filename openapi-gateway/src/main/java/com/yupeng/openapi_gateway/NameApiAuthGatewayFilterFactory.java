package com.yupeng.openapi_gateway;

import com.yupeng.openapi.auth.rpc.GetUserAuthByAccessKeyResponse;
import com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse;
import com.yupeng.openapi_gateway.grpc.InterfaceQuotaGrpcClient;
import com.yupeng.openapi_gateway.grpc.UserAuthGrpcClient;
import com.yupeng.openapiclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class NameApiAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<NameApiAuthGatewayFilterFactory.Config> {

    private static final long FIVE_MINUTES_IN_SECONDS = 5 * 60L;
    private static final String ATTR_USER_ID = "quota.userId";
    private static final String ATTR_INTERFACE_INFO_ID = "quota.interfaceInfoId";
    private static final String ATTR_QUOTA_RESERVED = "quota.reserved";

    private final UserAuthGrpcClient userAuthGrpcClient;
    private final InterfaceQuotaGrpcClient interfaceQuotaGrpcClient;

    public NameApiAuthGatewayFilterFactory(UserAuthGrpcClient userAuthGrpcClient,
                                           InterfaceQuotaGrpcClient interfaceQuotaGrpcClient) {
        super(Config.class);
        this.userAuthGrpcClient = userAuthGrpcClient;
        this.interfaceQuotaGrpcClient = interfaceQuotaGrpcClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            ServerHttpResponse response = exchange.getResponse();

            String accessKey = headers.getFirst("accessKey");
            String nonce = headers.getFirst("nonce");
            String sign = headers.getFirst("sign");
            String body = headers.getFirst("body");
            String timestamp = headers.getFirst("timestamp");

            if (!StringUtils.hasText(accessKey)
                    || !StringUtils.hasText(nonce)
                    || !StringUtils.hasText(sign)
                    || body == null
                    || !StringUtils.hasText(timestamp)) {
                return handleNoAuth(response);
            }

            long nonceValue;
            long timestampValue;
            try {
                nonceValue = Long.parseLong(nonce);
                timestampValue = Long.parseLong(timestamp);
            } catch (NumberFormatException e) {
                return handleNoAuth(response);
            }

            long currentTime = System.currentTimeMillis() / 1000;
            if (nonceValue <= 0L || timestampValue <= 0L) {
                return handleNoAuth(response);
            }
            if (Math.abs(currentTime - timestampValue) > FIVE_MINUTES_IN_SECONDS) {
                return handleNoAuth(response);
            }

            return userAuthGrpcClient.getUserAuthByAccessKey(accessKey)
                    .onErrorMap(e -> new GatewayGrpcException("Auth gRPC lookup failed", e))
                    //When get the response, unpack the wrapper and return another Mono<Void>
                    .flatMap(authResponse -> verifyReserveAndContinue(
                            authResponse, accessKey, sign, body, response, chain, exchange))
                    .onErrorResume(GatewayGrpcException.class, e -> {
                        log.error(e.getMessage(), e);
                        return handleServiceUnavailable(response);
                    });
        };
    }

    private Mono<Void> verifyReserveAndContinue(GetUserAuthByAccessKeyResponse authResponse,
                                                String accessKey,
                                                String sign,
                                                String body,
                                                ServerHttpResponse response,
                                                GatewayFilterChain chain,
                                                ServerWebExchange exchange) {
        if (!authResponse.getFound()) {
            return handleNoAuth(response);
        }
        if (!authResponse.getActive()) {
            return handleNoAuth(response);
        }
        if (authResponse.getUserId() <= 0L) {
            return handleNoAuth(response);
        }
        if (!accessKey.equals(authResponse.getAccessKey())) {
            return handleNoAuth(response);
        }

        String serverSign = SignUtils.genSign(body, authResponse.getSecretKey());
        if (!serverSign.equals(sign)) {
            return handleNoAuth(response);
        }

        //Define request params for interfaceQuotaGrpcClient.reserveInvokeQuota() method
        String requestPath = exchange.getRequest().getURI().getRawPath();
        HttpMethod httpMethod = exchange.getRequest().getMethod();
        String requestMethod = httpMethod == null ? "" : httpMethod.name();

        //invoke interfaceQuotaGrpcClient.reserveInvokeQuota
        return interfaceQuotaGrpcClient.reserveInvokeQuota(authResponse.getUserId(), requestPath, requestMethod)
                .onErrorMap(e -> new GatewayGrpcException("Quota reserve gRPC call failed", e))
                .flatMap(reserveResponse -> handleReservedQuota(
                        reserveResponse, authResponse.getUserId(), response, chain, exchange));

    }

    //handleReservedQuota method definees how deal with the data returns from reserveInvokeQuota
    private Mono<Void> handleReservedQuota(ReserveInvokeQuotaResponse reserveResponse,
                                           long userId,
                                           ServerHttpResponse response,
                                           GatewayFilterChain chain,
                                           ServerWebExchange exchange) {
        if (!reserveResponse.getAllowed() || reserveResponse.getInterfaceInfoId() <= 0L) {
            return handleNoAuth(response);
        }


        long interfaceInfoId = reserveResponse.getInterfaceInfoId();
        //exchange represents the whole conversation context, and we store request-scoped data and pass the data to later code
        exchange.getAttributes().put(ATTR_USER_ID, userId);
        exchange.getAttributes().put(ATTR_INTERFACE_INFO_ID, interfaceInfoId);
        exchange.getAttributes().put(ATTR_QUOTA_RESERVED, Boolean.TRUE);

        return chain.filter(exchange)
                .then(finalizeQuotaAfterSuccess(exchange, userId, interfaceInfoId))
                .onErrorResume(throwable -> rollbackAfterFailure(userId, interfaceInfoId, throwable));
    }


    //After downstream API has finished, decide whether to consume the quota or give it back
    private Mono<Void> finalizeQuotaAfterSuccess(ServerWebExchange exchange, long userId, long interfaceInfoId) {
        HttpStatusCode statusCode = exchange.getResponse().getStatusCode();
        // A null status usually means the framework will fall back to the normal 200 default.
        boolean success = statusCode == null || statusCode.is2xxSuccessful();
        if (success) {
            return interfaceQuotaGrpcClient.commitInvokeQuota(userId, interfaceInfoId)
                    .doOnNext(committed -> {
                        if (!committed) {
                            log.error("Quota commit returned false for userId={}, interfaceInfoId={}",
                                    userId, interfaceInfoId);
                        }
                    })
                    .onErrorResume(e -> {
                        log.error("Quota commit failed for userId={}, interfaceInfoId={}",
                                userId, interfaceInfoId, e);
                        return Mono.just(false);
                    })
                    //Ignore the current return type which is Mono<Void>, only wait till completion
                    .then();
        }
        return rollbackQuota(userId, interfaceInfoId,
                "downstream status " + (statusCode == null ? "UNKNOWN" : statusCode.value()));
    }

    private Mono<Void> rollbackAfterFailure(long userId, long interfaceInfoId, Throwable throwable) {
        return rollbackQuota(userId, interfaceInfoId, "downstream exception")
                .then(Mono.error(throwable));
    }

    private Mono<Void> rollbackQuota(long userId, long interfaceInfoId, String reason) {
        return interfaceQuotaGrpcClient.rollbackInvokeQuota(userId, interfaceInfoId)
                .doOnNext(rolledBack -> {
                    if (!rolledBack) {
                        log.error("Quota rollback returned false for userId={}, interfaceInfoId={}, reason={}",
                                userId, interfaceInfoId, reason);
                    }
                })
                .onErrorResume(e -> {
                    log.error("Quota rollback failed for userId={}, interfaceInfoId={}, reason={}",
                            userId, interfaceInfoId, reason, e);
                    return Mono.just(false);
                })
                .then();
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleServiceUnavailable(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        return response.setComplete();
    }

    public static class Config {
    }

    private static final class GatewayGrpcException extends RuntimeException {
        private GatewayGrpcException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
