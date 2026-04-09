package com.yupeng.openapi.grpc;

import com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest;
import com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse;
import com.yupeng.openapi.auth.rpc.InterfaceQuotaServiceGrpc;
import com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest;
import com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse;
import com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest;
import com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse;
import com.yupeng.openapi.model.entity.InterfaceInfo;
import com.yupeng.openapi.service.InterfaceInfoService;
import com.yupeng.openapi.service.UserInterfaceInfoService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * gRPC quota service exposed to the gateway.
 *
 * Flow for new learners:
 * 1. Gateway already authenticated the caller and knows userId.
 * 2. Gateway asks backend to reserve quota for the current request path + method.
 * 3. Backend resolves that request into a concrete interface_info row.
 * 4. Backend decrements leftNum atomically if quota is available.
 * 5. After the downstream response, the gateway calls commit or rollback.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InterfaceQuotaGrpcService extends InterfaceQuotaServiceGrpc.InterfaceQuotaServiceImplBase {

    private final InterfaceInfoService interfaceInfoService;

    private final UserInterfaceInfoService userInterfaceInfoService;



    /**
     * message ReserveInvokeQuotaRequest {
     *   int64 user_id = 1;
     *   string request_path = 2;
     *   string request_method = 3;
     * }
     *
     * message ReserveInvokeQuotaResponse {
     *   bool allowed = 1;
     *   int64 interface_info_id = 2;
     * }
     *
     * StreamObserver is designed for gRPC server api, so that it can support both unary and streaming RPCs,
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void reserveInvokeQuota(ReserveInvokeQuotaRequest request,
                                   StreamObserver<ReserveInvokeQuotaResponse> responseObserver) {

        try{
            ReserveInvokeQuotaResponse.Builder responseBuilder = ReserveInvokeQuotaResponse.newBuilder();
            if(request.getUserId() > 0){
                InterfaceInfo interfaceInfo = interfaceInfoService.getOnlineInterfaceInfoByPathAndMethod(
                        request.getRequestPath(),
                        request.getRequestMethod()
                );
                if(interfaceInfo != null && interfaceInfo.getId() != null){
                    boolean allowed = userInterfaceInfoService.reserveInvokeQuota(interfaceInfo.getId(), request.getUserId());
                    if(allowed){
                        responseBuilder.setAllowed(true)
                                .setInterfaceInfoId(interfaceInfo.getId());
                    }
                }
                responseObserver.onNext(responseBuilder.build());
                responseObserver.onCompleted();
            }
        }catch (Exception e){
            log.error("Failed to reserve invoke quota for userId={}, path={}, method={}", request.getUserId(), request.getRequestPath(), request.getRequestMethod());
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to reverse invoke quota")
                    .withCause(e)
                    .asRuntimeException()
            );
        }



//        try {
//            ReserveInvokeQuotaResponse.Builder responseBuilder = ReserveInvokeQuotaResponse.newBuilder();
//            if (request.getUserId() > 0) {
//                InterfaceInfo interfaceInfo = interfaceInfoService.getOnlineInterfaceInfoByPathAndMethod(
//                        request.getRequestPath(),
//                        request.getRequestMethod()
//                );
//                if (interfaceInfo != null && interfaceInfo.getId() != null) {
//                    boolean allowed = userInterfaceInfoService.reserveInvokeQuota(interfaceInfo.getId(), request.getUserId());
//                    if (allowed) {
//                        responseBuilder.setAllowed(true)
//                                .setInterfaceInfoId(interfaceInfo.getId());
//                    }
//                }
//            }
//            responseObserver.onNext(responseBuilder.build());
//            responseObserver.onCompleted();
//        } catch (Exception e) {
//            log.error("Failed to reserve invoke quota for userId={}, path={}, method={}",
//                    request.getUserId(), request.getRequestPath(), request.getRequestMethod(), e);
//            responseObserver.onError(Status.INTERNAL
//                    .withDescription("Failed to reserve invoke quota")
//                    .withCause(e)
//                    .asRuntimeException());
//        }
    }

    @Override
    public void commitInvokeQuota(CommitInvokeQuotaRequest request,
                                  StreamObserver<CommitInvokeQuotaResponse> responseObserver) {
        try {
            boolean success = request.getUserId() > 0
                    && request.getInterfaceInfoId() > 0
                    && userInterfaceInfoService.commitInvokeQuota(request.getInterfaceInfoId(), request.getUserId());
            responseObserver.onNext(CommitInvokeQuotaResponse.newBuilder()
                    .setSuccess(success)
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Failed to commit invoke quota for userId={}, interfaceInfoId={}",
                    request.getUserId(), request.getInterfaceInfoId(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to commit invoke quota")
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    @Override
    public void rollbackInvokeQuota(RollbackInvokeQuotaRequest request,
                                    StreamObserver<RollbackInvokeQuotaResponse> responseObserver) {
        try {
            boolean success = request.getUserId() > 0
                    && request.getInterfaceInfoId() > 0
                    && userInterfaceInfoService.rollbackInvokeQuota(request.getInterfaceInfoId(), request.getUserId());
            responseObserver.onNext(RollbackInvokeQuotaResponse.newBuilder()
                    .setSuccess(success)
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Failed to roll back invoke quota for userId={}, interfaceInfoId={}",
                    request.getUserId(), request.getInterfaceInfoId(), e);
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to roll back invoke quota")
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}
