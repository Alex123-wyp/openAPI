package com.yupi.openapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Backend gRPC auth server configuration.
 */
@Data
@Component
@ConfigurationProperties(prefix = "openapi.auth-grpc")
public class AuthGrpcProperties {

    /**
     * Dedicated gRPC port used by the gateway auth lookup.
     */
    private int port = 9091;
}
