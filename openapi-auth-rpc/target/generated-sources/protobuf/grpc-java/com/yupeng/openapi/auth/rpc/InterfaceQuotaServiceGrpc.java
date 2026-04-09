package com.yupeng.openapi.auth.rpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * This gRPC service lets the gateway manage invoke quota before and after it
 * forwards a public interface request.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.63.0)",
    comments = "Source: user_auth.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class InterfaceQuotaServiceGrpc {

  private InterfaceQuotaServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "userauth.InterfaceQuotaService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest,
      com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse> getReserveInvokeQuotaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReserveInvokeQuota",
      requestType = com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest.class,
      responseType = com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest,
      com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse> getReserveInvokeQuotaMethod() {
    io.grpc.MethodDescriptor<com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest, com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse> getReserveInvokeQuotaMethod;
    if ((getReserveInvokeQuotaMethod = InterfaceQuotaServiceGrpc.getReserveInvokeQuotaMethod) == null) {
      synchronized (InterfaceQuotaServiceGrpc.class) {
        if ((getReserveInvokeQuotaMethod = InterfaceQuotaServiceGrpc.getReserveInvokeQuotaMethod) == null) {
          InterfaceQuotaServiceGrpc.getReserveInvokeQuotaMethod = getReserveInvokeQuotaMethod =
              io.grpc.MethodDescriptor.<com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest, com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReserveInvokeQuota"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InterfaceQuotaServiceMethodDescriptorSupplier("ReserveInvokeQuota"))
              .build();
        }
      }
    }
    return getReserveInvokeQuotaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest,
      com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse> getCommitInvokeQuotaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CommitInvokeQuota",
      requestType = com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest.class,
      responseType = com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest,
      com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse> getCommitInvokeQuotaMethod() {
    io.grpc.MethodDescriptor<com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest, com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse> getCommitInvokeQuotaMethod;
    if ((getCommitInvokeQuotaMethod = InterfaceQuotaServiceGrpc.getCommitInvokeQuotaMethod) == null) {
      synchronized (InterfaceQuotaServiceGrpc.class) {
        if ((getCommitInvokeQuotaMethod = InterfaceQuotaServiceGrpc.getCommitInvokeQuotaMethod) == null) {
          InterfaceQuotaServiceGrpc.getCommitInvokeQuotaMethod = getCommitInvokeQuotaMethod =
              io.grpc.MethodDescriptor.<com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest, com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CommitInvokeQuota"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InterfaceQuotaServiceMethodDescriptorSupplier("CommitInvokeQuota"))
              .build();
        }
      }
    }
    return getCommitInvokeQuotaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest,
      com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse> getRollbackInvokeQuotaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RollbackInvokeQuota",
      requestType = com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest.class,
      responseType = com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest,
      com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse> getRollbackInvokeQuotaMethod() {
    io.grpc.MethodDescriptor<com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest, com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse> getRollbackInvokeQuotaMethod;
    if ((getRollbackInvokeQuotaMethod = InterfaceQuotaServiceGrpc.getRollbackInvokeQuotaMethod) == null) {
      synchronized (InterfaceQuotaServiceGrpc.class) {
        if ((getRollbackInvokeQuotaMethod = InterfaceQuotaServiceGrpc.getRollbackInvokeQuotaMethod) == null) {
          InterfaceQuotaServiceGrpc.getRollbackInvokeQuotaMethod = getRollbackInvokeQuotaMethod =
              io.grpc.MethodDescriptor.<com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest, com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RollbackInvokeQuota"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse.getDefaultInstance()))
              .setSchemaDescriptor(new InterfaceQuotaServiceMethodDescriptorSupplier("RollbackInvokeQuota"))
              .build();
        }
      }
    }
    return getRollbackInvokeQuotaMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InterfaceQuotaServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InterfaceQuotaServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InterfaceQuotaServiceStub>() {
        @java.lang.Override
        public InterfaceQuotaServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InterfaceQuotaServiceStub(channel, callOptions);
        }
      };
    return InterfaceQuotaServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InterfaceQuotaServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InterfaceQuotaServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InterfaceQuotaServiceBlockingStub>() {
        @java.lang.Override
        public InterfaceQuotaServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InterfaceQuotaServiceBlockingStub(channel, callOptions);
        }
      };
    return InterfaceQuotaServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InterfaceQuotaServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InterfaceQuotaServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InterfaceQuotaServiceFutureStub>() {
        @java.lang.Override
        public InterfaceQuotaServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InterfaceQuotaServiceFutureStub(channel, callOptions);
        }
      };
    return InterfaceQuotaServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * This gRPC service lets the gateway manage invoke quota before and after it
   * forwards a public interface request.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Reserve one available invocation slot before the request is forwarded.
     * </pre>
     */
    default void reserveInvokeQuota(com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest request,
        io.grpc.stub.StreamObserver<com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReserveInvokeQuotaMethod(), responseObserver);
    }

    /**
     * <pre>
     * Mark a reserved invocation as successfully consumed after a 2xx response.
     * </pre>
     */
    default void commitInvokeQuota(com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest request,
        io.grpc.stub.StreamObserver<com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCommitInvokeQuotaMethod(), responseObserver);
    }

    /**
     * <pre>
     * Give the reserved slot back if the downstream invocation fails.
     * </pre>
     */
    default void rollbackInvokeQuota(com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest request,
        io.grpc.stub.StreamObserver<com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRollbackInvokeQuotaMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service InterfaceQuotaService.
   * <pre>
   * This gRPC service lets the gateway manage invoke quota before and after it
   * forwards a public interface request.
   * </pre>
   */
  public static abstract class InterfaceQuotaServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return InterfaceQuotaServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service InterfaceQuotaService.
   * <pre>
   * This gRPC service lets the gateway manage invoke quota before and after it
   * forwards a public interface request.
   * </pre>
   */
  public static final class InterfaceQuotaServiceStub
      extends io.grpc.stub.AbstractAsyncStub<InterfaceQuotaServiceStub> {
    private InterfaceQuotaServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InterfaceQuotaServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InterfaceQuotaServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Reserve one available invocation slot before the request is forwarded.
     * </pre>
     */
    public void reserveInvokeQuota(com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest request,
        io.grpc.stub.StreamObserver<com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReserveInvokeQuotaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Mark a reserved invocation as successfully consumed after a 2xx response.
     * </pre>
     */
    public void commitInvokeQuota(com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest request,
        io.grpc.stub.StreamObserver<com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCommitInvokeQuotaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Give the reserved slot back if the downstream invocation fails.
     * </pre>
     */
    public void rollbackInvokeQuota(com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest request,
        io.grpc.stub.StreamObserver<com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRollbackInvokeQuotaMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service InterfaceQuotaService.
   * <pre>
   * This gRPC service lets the gateway manage invoke quota before and after it
   * forwards a public interface request.
   * </pre>
   */
  public static final class InterfaceQuotaServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<InterfaceQuotaServiceBlockingStub> {
    private InterfaceQuotaServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InterfaceQuotaServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InterfaceQuotaServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Reserve one available invocation slot before the request is forwarded.
     * </pre>
     */
    public com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse reserveInvokeQuota(com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReserveInvokeQuotaMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Mark a reserved invocation as successfully consumed after a 2xx response.
     * </pre>
     */
    public com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse commitInvokeQuota(com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCommitInvokeQuotaMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Give the reserved slot back if the downstream invocation fails.
     * </pre>
     */
    public com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse rollbackInvokeQuota(com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRollbackInvokeQuotaMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service InterfaceQuotaService.
   * <pre>
   * This gRPC service lets the gateway manage invoke quota before and after it
   * forwards a public interface request.
   * </pre>
   */
  public static final class InterfaceQuotaServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<InterfaceQuotaServiceFutureStub> {
    private InterfaceQuotaServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InterfaceQuotaServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InterfaceQuotaServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Reserve one available invocation slot before the request is forwarded.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse> reserveInvokeQuota(
        com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReserveInvokeQuotaMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Mark a reserved invocation as successfully consumed after a 2xx response.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse> commitInvokeQuota(
        com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCommitInvokeQuotaMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Give the reserved slot back if the downstream invocation fails.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse> rollbackInvokeQuota(
        com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRollbackInvokeQuotaMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RESERVE_INVOKE_QUOTA = 0;
  private static final int METHODID_COMMIT_INVOKE_QUOTA = 1;
  private static final int METHODID_ROLLBACK_INVOKE_QUOTA = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RESERVE_INVOKE_QUOTA:
          serviceImpl.reserveInvokeQuota((com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest) request,
              (io.grpc.stub.StreamObserver<com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse>) responseObserver);
          break;
        case METHODID_COMMIT_INVOKE_QUOTA:
          serviceImpl.commitInvokeQuota((com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest) request,
              (io.grpc.stub.StreamObserver<com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse>) responseObserver);
          break;
        case METHODID_ROLLBACK_INVOKE_QUOTA:
          serviceImpl.rollbackInvokeQuota((com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest) request,
              (io.grpc.stub.StreamObserver<com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getReserveInvokeQuotaMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaRequest,
              com.yupeng.openapi.auth.rpc.ReserveInvokeQuotaResponse>(
                service, METHODID_RESERVE_INVOKE_QUOTA)))
        .addMethod(
          getCommitInvokeQuotaMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.yupeng.openapi.auth.rpc.CommitInvokeQuotaRequest,
              com.yupeng.openapi.auth.rpc.CommitInvokeQuotaResponse>(
                service, METHODID_COMMIT_INVOKE_QUOTA)))
        .addMethod(
          getRollbackInvokeQuotaMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaRequest,
              com.yupeng.openapi.auth.rpc.RollbackInvokeQuotaResponse>(
                service, METHODID_ROLLBACK_INVOKE_QUOTA)))
        .build();
  }

  private static abstract class InterfaceQuotaServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    InterfaceQuotaServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.yupeng.openapi.auth.rpc.UserAuthProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("InterfaceQuotaService");
    }
  }

  private static final class InterfaceQuotaServiceFileDescriptorSupplier
      extends InterfaceQuotaServiceBaseDescriptorSupplier {
    InterfaceQuotaServiceFileDescriptorSupplier() {}
  }

  private static final class InterfaceQuotaServiceMethodDescriptorSupplier
      extends InterfaceQuotaServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    InterfaceQuotaServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (InterfaceQuotaServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InterfaceQuotaServiceFileDescriptorSupplier())
              .addMethod(getReserveInvokeQuotaMethod())
              .addMethod(getCommitInvokeQuotaMethod())
              .addMethod(getRollbackInvokeQuotaMethod())
              .build();
        }
      }
    }
    return result;
  }
}
