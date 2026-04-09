package com.yupeng.openapi.grpc;

import com.yupeng.openapi.auth.rpc.GetUserAuthByAccessKeyRequest;
import com.yupeng.openapi.auth.rpc.GetUserAuthByAccessKeyResponse;
import com.yupeng.openapi.auth.rpc.UserAuthServiceGrpc;
import com.yupeng.openapi.model.dto.auth.UserAuthInfo;
import com.yupeng.openapi.service.UserService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * gRPC auth lookup exposed to the gateway.
 *
 * Request flow for new learners:
 * 1. Gateway receives /api/name/** with accessKey/sign/timestamp headers.
 * 2. Gateway does not query MySQL directly.
 * 3. Gateway calls this gRPC service on the backend.
 * 4. This service asks UserService to query the user table.
 * 5. This service maps the Java result into the protobuf response.
 * 6. Gateway uses the returned secretKey to verify the incoming sign header.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthGrpcService extends UserAuthServiceGrpc.UserAuthServiceImplBase {

    private final UserService userService;

    @Override
    public void getUserAuthByAccessKey(GetUserAuthByAccessKeyRequest request,
                                       StreamObserver<GetUserAuthByAccessKeyResponse> responseObserver) {
        try {
            // gRPC request message -> backend Java service call.
            UserAuthInfo userAuthInfo = userService.getUserAuthInfoByAccessKey(request.getAccessKey());

            // Protobuf messages are immutable once built, so we use a builder first.
            GetUserAuthByAccessKeyResponse.Builder responseBuilder = GetUserAuthByAccessKeyResponse.newBuilder();
            if (userAuthInfo != null) {
                // Only return the minimum auth data needed by the gateway.
                responseBuilder.setFound(true)
                        .setUserId(userAuthInfo.getUserId() == null ? 0L : userAuthInfo.getUserId())
                        .setAccessKey(userAuthInfo.getAccessKey() == null ? "" : userAuthInfo.getAccessKey())
                        .setSecretKey(userAuthInfo.getSecretKey() == null ? "" : userAuthInfo.getSecretKey())
                        .setActive(userAuthInfo.isActive());
            }
            // onNext sends one response message back to the caller.
            responseObserver.onNext(responseBuilder.build());
            // onCompleted tells gRPC that this unary RPC is finished successfully.
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("Failed to handle auth gRPC lookup for accessKey={}", request.getAccessKey(), e);
            // Convert backend exceptions into a gRPC error status for the gateway.
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to query auth info")
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}
