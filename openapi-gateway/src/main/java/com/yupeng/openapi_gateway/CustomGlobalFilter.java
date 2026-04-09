package com.yupeng.openapi_gateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * Global Filter
 */

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1", "::1", "0:0:0:0:0:0:0:1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //Request Logs
        ServerHttpRequest request = exchange.getRequest();
        log.info("Request ID: " + request.getId());
        log.info("Request path: " +  request.getPath().value());
        log.info("Request method: " +  request.getMethod());
        log.info("Request params: " +  request.getQueryParams());
        String sourceAddress = request.getRemoteAddress() == null
                ? "unknown"
                : request.getRemoteAddress().getAddress().getHostAddress();
        log.info("Request origin address: " + sourceAddress);


        log.info("Request address: " +  request.getRemoteAddress());
        log.info("custom global filter");
        log.info("Headers: " + request.getHeaders());
        ServerHttpResponse response = exchange.getResponse();

        //Access control
        if(!IP_WHITE_LIST.contains(sourceAddress)){
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }

        //User Authentication
//        HttpHeaders headers = exchange.getRequest().getHeaders();
//        String accessKey = headers.getFirst("accessKey");
//        String nonce = headers.getFirst("nonce");
//        String sign = headers.getFirst("sign");
//        String body = headers.getFirst("body");
//        String timestamp = headers.getFirst("timestamp");
//        String realAccessKey = "ak_a6ec8ee09db3066c63aebc4980d3bb2b";
//        //todo real accessKey comes from the real database (RPC)
//        Long currentTime = System.currentTimeMillis() / 1000;
//        Long FIVE_MINUTES = 5 * 60L;
//        if((currentTime - Long.parseLong(timestamp)) > FIVE_MINUTES){
//            return handleNoAuth(response);
//        }
//        if(!realAccessKey.equals(accessKey)){
//            return handleNoAuth(response);
//        }
//        if(Long.parseLong(nonce) > 10000L){
//            return handleNoAuth(response);
//        }
//        String serverSign = SignUtils.genSign(body, "sk_954ee91d44875f13f21610af5432f59ba089db9d8c5a4ed8");
//        if(!serverSign.equals(sign)){
//            return handleNoAuth(response);
//        }
//
//        //todo Find mock interfaces from the real database, and related method as well to check if the valid interface exists
//        //Continue processing
//        Mono<Void> filter = chain.filter(exchange);
//        //Response log
//        //Invocation failed, return error code
//        if(response.getStatusCode() != HttpStatus.OK){
//            return handleInvokeError(response);
//        }

        //let the request continue
        return chain.filter(exchange);
    }

    //Forward request
    @Override
    public int getOrder() {
        return -1;
    }
}
