package com.yupeng.openapi_gateway.grpc;

import com.yupeng.openapi.auth.rpc.InterfaceQuotaServiceGrpc;
import com.yupeng.openapi.auth.rpc.UserAuthServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * gRPC client beans used by the gateway auth + quota filter flow.
 *
 * These beans are the gateway-side equivalent of an HTTP client:
 * - ManagedChannel = the network connection pool / transport
 * - FutureStub = the generated strongly-typed client
 */
@Configuration
public class AuthGrpcClientConfig {

    @Bean(destroyMethod = "shutdownNow")
    public ManagedChannel userAuthManagedChannel(AuthGrpcProperties authGrpcProperties) {
        // usePlaintext() means no TLS for local dev traffic between gateway and backend.
        return ManagedChannelBuilder.forAddress(authGrpcProperties.getHost(), authGrpcProperties.getPort())
                .usePlaintext()
                .build();
    }

    @Bean
    public UserAuthServiceGrpc.UserAuthServiceFutureStub userAuthServiceFutureStub(ManagedChannel userAuthManagedChannel) {
        // FutureStub is important here because the gateway is WebFlux/reactive.
        // A blocking stub would be easier to write, but it would block the event loop.
        return UserAuthServiceGrpc.newFutureStub(userAuthManagedChannel);
    }

    @Bean
    public InterfaceQuotaServiceGrpc.InterfaceQuotaServiceFutureStub interfaceQuotaServiceFutureStub(
            ManagedChannel userAuthManagedChannel) {
        // Reuse the same channel because quota and auth are served by the same
        // backend gRPC server on the same host/port.
        return InterfaceQuotaServiceGrpc.newFutureStub(userAuthManagedChannel);
    }
}
