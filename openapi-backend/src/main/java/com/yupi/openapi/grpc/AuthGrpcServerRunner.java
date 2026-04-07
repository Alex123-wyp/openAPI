package com.yupi.openapi.grpc;

import com.yupi.openapi.config.AuthGrpcProperties;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Starts and stops the backend gRPC auth server with the Spring app lifecycle.
 *
 * Why this class exists:
 * Spring Boot knows how to start the normal HTTP server for /api/**,
 * but plain grpc-java does not auto-start itself in this project.
 * This component bridges that gap:
 * - when Spring starts, we start the gRPC server on port 9091
 * - when Spring stops, we shut the gRPC server down cleanly
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthGrpcServerRunner {

    private final AuthGrpcProperties authGrpcProperties;

    private final UserAuthGrpcService userAuthGrpcService;

    // io.grpc.Server is the actual TCP server that listens for gRPC calls.
    private Server server;

    @PostConstruct
    public void start() {
        try {
            // Register our gRPC service implementation and start listening.
            server = ServerBuilder.forPort(authGrpcProperties.getPort())
                    .addService(userAuthGrpcService)
                    .build()
                    .start();
            log.info("Auth gRPC server started on port {}", authGrpcProperties.getPort());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to start auth gRPC server", e);
        }
    }

    @PreDestroy
    public void stop() {
        if (server == null) {
            return;
        }
        log.info("Shutting down auth gRPC server");
        // First ask the server to stop gracefully so in-flight RPCs can finish.
        server.shutdown();
        try {
            // Wait briefly; if it is still busy, force-stop it.
            if (!server.awaitTermination(5, TimeUnit.SECONDS)) {
                server.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            server.shutdownNow();
        }
    }
}
