package com.yupeng.openapiclientsdk.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.yupeng.openapiclientsdk.exception.OpenApiClientException;
import com.yupeng.openapiclientsdk.modal.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpenApiClientTest {

    private HttpServer httpServer;
    private ExecutorService executorService;

    @AfterEach
    void tearDown() {
        if (httpServer != null) {
            httpServer.stop(0);
        }
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }

    @Test
    void getNameByGetShouldReturnBody() throws Exception {
        startServer(exchange -> {
            assertEquals("/api/name/", exchange.getRequestURI().getPath());
            assertEquals("name=alex", exchange.getRequestURI().getQuery());
            writeResponse(exchange, 200, "GET: Your name is alex");
        });

        OpenApiClient client = new OpenApiClient(baseUrl(), "ak", "sk");

        String result = client.getNameByGet("alex");

        assertEquals("GET: Your name is alex", result);
    }

    @Test
    void getUserNameByPostShouldReturnBody() throws Exception {
        startServer(exchange -> {
            assertEquals("/api/name/user", exchange.getRequestURI().getPath());
            assertEquals("POST", exchange.getRequestMethod());
            String requestBody = readRequestBody(exchange);
            assertTrue(requestBody.contains("\"name\":\"alex\""));
            writeResponse(exchange, 200, "POST: Your username is alex");
        });

        OpenApiClient client = new OpenApiClient(baseUrl(), "ak", "sk");
        User user = new User();
        user.setName("alex");

        String result = client.getUserNameByPost(user);

        assertEquals("POST: Your username is alex", result);
    }

    @Test
    void non2xxResponseShouldThrowExceptionWithStatusAndBody() throws Exception {
        startServer(exchange -> writeResponse(exchange, 500, "gateway failed"));

        OpenApiClient client = new OpenApiClient(baseUrl(), "ak", "sk");
        User user = new User();
        user.setName("alex");

        OpenApiClientException exception = assertThrows(OpenApiClientException.class,
                () -> client.getUserNameByPost(user));

        assertEquals(Integer.valueOf(500), exception.getStatusCode());
        assertEquals("gateway failed", exception.getResponseBody());
    }

    @Test
    void timeoutShouldThrowExceptionQuickly() throws Exception {
        startServer(exchange -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            writeResponse(exchange, 200, "slow response");
        });

        OpenApiClient client = new OpenApiClient(baseUrl(), "ak", "sk", 100, 100);
        User user = new User();
        user.setName("alex");

        OpenApiClientException exception = assertThrows(OpenApiClientException.class,
                () -> client.getUserNameByPost(user));

        assertTrue(exception.getMessage().contains("Gateway request failed"));
    }

    private void startServer(ExchangeHandler handler) throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(0), 0);
        executorService = Executors.newCachedThreadPool();
        httpServer.setExecutor(executorService);
        httpServer.createContext("/", exchange -> {
            try {
                handler.handle(exchange);
            } finally {
                exchange.close();
            }
        });
        httpServer.start();
    }

    private String baseUrl() {
        return "http://127.0.0.1:" + httpServer.getAddress().getPort();
    }

    private String readRequestBody(HttpExchange exchange) throws IOException {
        try (InputStream inputStream = exchange.getRequestBody()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private void writeResponse(HttpExchange exchange, int statusCode, String body) throws IOException {
        byte[] responseBytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        exchange.getResponseBody().write(responseBytes);
    }

    @FunctionalInterface
    private interface ExchangeHandler {
        void handle(HttpExchange exchange) throws IOException;
    }
}
