package com.yupi.openapi_gateway;

import com.yupi.openapi.auth.rpc.GetUserAuthByAccessKeyResponse;
import com.yupi.openapiclientsdk.utils.SignUtils;
import com.yupi.openapi_gateway.grpc.UserAuthGrpcClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class NameApiAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<NameApiAuthGatewayFilterFactory.Config> {

    // The request must be recent enough to reduce replay attacks.
    private static final long FIVE_MINUTES_IN_SECONDS = 5 * 60L;

    private final UserAuthGrpcClient userAuthGrpcClient;

    public NameApiAuthGatewayFilterFactory(UserAuthGrpcClient userAuthGrpcClient){
        super(Config.class);
        this.userAuthGrpcClient = userAuthGrpcClient;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Step 1: read the auth-related headers added by the SDK request.
            HttpHeaders headers = exchange.getRequest().getHeaders();
            ServerHttpResponse response = exchange.getResponse();
            String accessKey = headers.getFirst("accessKey");
            String nonce = headers.getFirst("nonce");
            String sign = headers.getFirst("sign");
            String body = headers.getFirst("body");
            String timestamp = headers.getFirst("timestamp");

            if(!StringUtils.hasText(accessKey)
                    || !StringUtils.hasText(nonce)
                    || !StringUtils.hasText(sign)
                    || body == null
                    || !StringUtils.hasText(timestamp)){
                // Missing any required header means the gateway cannot verify the request.
                return handleNoAuth(response);
            }

            long nonceValue;
            long timestampValue;
            try{
                // Step 2: convert string headers to numbers once and reuse them.
                nonceValue = Long.parseLong(nonce);
                timestampValue = Long.parseLong(timestamp);
            } catch (NumberFormatException e) {
                return handleNoAuth(response);
            }

            // Step 3: quick local validation before making the remote gRPC call.
            long currentTime = System.currentTimeMillis() / 1000;
            if(nonceValue <= 0L || timestampValue <= 0L){
                return handleNoAuth(response);
            }
            if(Math.abs(currentTime - timestampValue) > FIVE_MINUTES_IN_SECONDS){
                return handleNoAuth(response);
            }

            // Step 4: ask the backend, "who owns this accessKey?"
            return userAuthGrpcClient.getUserAuthByAccessKey(accessKey)
                    .flatMap(authResponse -> verifyAndContinue(authResponse, accessKey, sign, body, response, chain, exchange))
                    .onErrorResume(e -> {
                        log.error("Auth gRPC lookup failed for accessKey={}", accessKey, e);
                        return handleServiceUnavailable(response);
                    });
        };
    }

    private Mono<Void> verifyAndContinue(GetUserAuthByAccessKeyResponse authResponse,
                                         String accessKey,
                                         String sign,
                                         String body,
                                         ServerHttpResponse response,
                                         org.springframework.cloud.gateway.filter.GatewayFilterChain chain,
                                         org.springframework.web.server.ServerWebExchange exchange) {
        // Step 5: reject unknown access keys.
        if (!authResponse.getFound()) {
            return handleNoAuth(response);
        }
        // Step 6: reject users that exist but are not allowed to invoke.
        if (!authResponse.getActive()) {
            return handleNoAuth(response);
        }
        // Step 7: a small defensive check to ensure the response matches the request key.
        if (!accessKey.equals(authResponse.getAccessKey())) {
            return handleNoAuth(response);
        }
        // Step 8: recompute the expected sign using the secretKey from the database.
        String serverSign = SignUtils.genSign(body, authResponse.getSecretKey());
        if (!serverSign.equals(sign)) {
            return handleNoAuth(response);
        }
        // Step 9: auth passed, so the request can continue to the public interface.
        return chain.filter(exchange);
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleServiceUnavailable(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        return response.setComplete();
    }

    public static class Config{
    }

}
