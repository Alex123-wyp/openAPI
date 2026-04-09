package com.yupeng.openapiclientsdk;
import com.yupeng.openapiclientsdk.client.OpenApiClient;
import lombok.Data;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConfigurationProperties(prefix = "openapi.client")
@Data
public class OpenApiClientConfig {
    private String accessKey;
    private String secretKey;
    private String gatewayHost;
    private int connectTimeoutMillis = OpenApiClient.DEFAULT_CONNECT_TIMEOUT_MILLIS;
    private int readTimeoutMillis = OpenApiClient.DEFAULT_READ_TIMEOUT_MILLIS;

    @Bean
    public OpenApiClient openApiClient() {
        return new OpenApiClient(gatewayHost, accessKey, secretKey, connectTimeoutMillis, readTimeoutMillis);
    }

}
