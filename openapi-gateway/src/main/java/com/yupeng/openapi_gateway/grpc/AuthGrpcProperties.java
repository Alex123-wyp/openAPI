package com.yupeng.openapi_gateway.grpc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Gateway gRPC client configuration for auth + quota lookups.
 */
@Data
@Component
@ConfigurationProperties(prefix = "openapi.auth-grpc")
public class AuthGrpcProperties {

    private String host = "127.0.0.1";

    private int port = 9091;

    private long deadlineMillis = 500L;
}
