package com.yupeng.openapi_gateway.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest;
import com.yupeng.openapi.auth.rpc.InterfaceQuotaServiceGrpc;
import com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest;
import com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse;
import com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Reactive wrapper around the gateway's quota management gRPC calls.
 * stub is the client-side proxy used to call remote gRPC service
 *
 */
@Component
@RequiredArgsConstructor
public class InterfaceQuotaGrpcClient {

    private final InterfaceQuotaServiceGrpc.InterfaceQuotaServiceFutureStub interfaceQuotaServiceFutureStub;

    private final AuthGrpcProperties authGrpcProperties;

    public Mono<ReserveInvokeQuotaResponse> reserveInvokeQuota(long userId, String requestPath, String requestMethod) {
        ReserveInvokeQuotaRequest request = ReserveInvokeQuotaRequest.newBuilder()
                .setUserId(userId)
                .setRequestPath(requestPath)
                .setRequestMethod(requestMethod)
                .build();
        return toMono(interfaceQuotaServiceFutureStub
                .withDeadlineAfter(authGrpcProperties.getDeadlineMillis(), TimeUnit.MILLISECONDS)
                .reserveInvokeQuota(request));
    }

    public Mono<Boolean> commitInvokeQuota(long userId, long interfaceInfoId) {
        CommitInvokeQuotaRequest request = CommitInvokeQuotaRequest.newBuilder()
                .setUserId(userId)
                .setInterfaceInfoId(interfaceInfoId)
                .build();
        return toMono(interfaceQuotaServiceFutureStub
                .withDeadlineAfter(authGrpcProperties.getDeadlineMillis(), TimeUnit.MILLISECONDS)
                .commitInvokeQuota(request))
                .map(response -> response.getSuccess());
    }

    public Mono<Boolean> rollbackInvokeQuota(long userId, long interfaceInfoId) {
        RollbackInvokeQuotaRequest request = RollbackInvokeQuotaRequest.newBuilder()
                .setUserId(userId)
                .setInterfaceInfoId(interfaceInfoId)
                .build();
        return toMono(interfaceQuotaServiceFutureStub
                .withDeadlineAfter(authGrpcProperties.getDeadlineMillis(), TimeUnit.MILLISECONDS)
                .rollbackInvokeQuota(request))
                .map(response -> response.getSuccess());
    }

    private <T> Mono<T> toMono(ListenableFuture<T> future) {
        return Mono.create(sink -> future.addListener(() -> {
            try {
                sink.success(future.get());
            } catch (InterruptedException e) {
                /**
                 * Not guarantee that high up code uses the same thread. the toMono method may use a thread while future.get() may use another thread.
                 * So when the current thread is dead, we need Thread.currentThread().interrupt(); to let high up code know the thread is dead.
                 */
                Thread.currentThread().interrupt();
                sink.error(e);
            } catch (ExecutionException e) {
                sink.error(e.getCause() == null ? e : e.getCause());
            }
            //Executor for the listener
        }, Runnable::run));
    }
}
