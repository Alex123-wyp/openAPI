package com.yupi.openapiclientsdk.exception;

/**
 * Thrown when an SDK request to the gateway fails or times out.
 */
public class OpenApiClientException extends RuntimeException {

    private final Integer statusCode;
    private final String responseBody;

    public OpenApiClientException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = null;
        this.responseBody = null;
    }

    public OpenApiClientException(String message, Integer statusCode, String responseBody) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
