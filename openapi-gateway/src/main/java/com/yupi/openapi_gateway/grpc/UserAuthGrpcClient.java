package com.yupi.openapi_gateway.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import com.yupi.openapi.auth.rpc.GetUserAuthByAccessKeyRequest;
import com.yupi.openapi.auth.rpc.GetUserAuthByAccessKeyResponse;
import com.yupi.openapi.auth.rpc.UserAuthServiceGrpc;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Reactive wrapper around the gateway's gRPC auth lookup.
 *
 * grpc-java's future stub returns ListenableFuture.
 * Spring Cloud Gateway expects Reactor types such as Mono.
 * This class is the adapter layer between those two worlds.
 */
@Component
@RequiredArgsConstructor
public class UserAuthGrpcClient {

    private final UserAuthServiceGrpc.UserAuthServiceFutureStub userAuthServiceFutureStub;

    private final AuthGrpcProperties authGrpcProperties;

    public Mono<GetUserAuthByAccessKeyResponse> getUserAuthByAccessKey(String accessKey) {
        // Build the protobuf request that will be sent over the wire.
        GetUserAuthByAccessKeyRequest request = GetUserAuthByAccessKeyRequest.newBuilder()
                .setAccessKey(accessKey)
                .build();
        return Mono.create(sink -> {
            // Apply a deadline so auth failures return quickly instead of hanging forever.
            ListenableFuture<GetUserAuthByAccessKeyResponse> future = userAuthServiceFutureStub
                    .withDeadlineAfter(authGrpcProperties.getDeadlineMillis(), TimeUnit.MILLISECONDS)
                    .getUserAuthByAccessKey(request);
            future.addListener(() -> {
                try {
                    // Complete the Reactor Mono when the gRPC future succeeds.
                    sink.success(future.get());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    sink.error(e);
                } catch (ExecutionException e) {
                    // gRPC wraps the real failure cause in ExecutionException.
                    sink.error(e.getCause() == null ? e : e.getCause());
                }
            }, Runnable::run);
        });
    }
}
