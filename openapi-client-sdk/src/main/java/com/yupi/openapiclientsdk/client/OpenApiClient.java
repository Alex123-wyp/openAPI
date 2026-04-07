package com.yupi.openapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yupi.openapiclientsdk.modal.User;
import com.yupi.openapiclientsdk.exception.OpenApiClientException;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.util.Timeout;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import static com.yupi.openapiclientsdk.utils.SignUtils.genSign;

/**
 * Invoke 3-praty API client
 *
 * @Author yupewan(Alex Wang)
 */
public class OpenApiClient {

    public static final String DEFAULT_GATEWAY_HOST = "http://localhost:8123";
    public static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 3000;
    public static final int DEFAULT_READ_TIMEOUT_MILLIS = 5000;

    private final String gatewayHost;
    private final String accessKey;
    private final String secretKey;
    private final int connectTimeoutMillis;
    private final int readTimeoutMillis;
    private final CloseableHttpClient httpClient;


//    public OpenApiClient() {
//        this(DEFAULT_GATEWAY_HOST);
//    }

    public OpenApiClient(String gatewayHost, String accessKey, String secretKey) {
        this(gatewayHost, accessKey, secretKey, DEFAULT_CONNECT_TIMEOUT_MILLIS, DEFAULT_READ_TIMEOUT_MILLIS);
    }

    public OpenApiClient(String gatewayHost, String accessKey, String secretKey, int connectTimeoutMillis, int readTimeoutMillis) {
        String normalizedGatewayHost = StrUtil.isBlank(gatewayHost) ? DEFAULT_GATEWAY_HOST : gatewayHost;
        this.gatewayHost = StrUtil.removeSuffix(normalizedGatewayHost, "/");
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.connectTimeoutMillis = connectTimeoutMillis > 0 ? connectTimeoutMillis : DEFAULT_CONNECT_TIMEOUT_MILLIS;
        this.readTimeoutMillis = readTimeoutMillis > 0 ? readTimeoutMillis : DEFAULT_READ_TIMEOUT_MILLIS;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(this.connectTimeoutMillis))
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(this.connectTimeoutMillis))
                .setResponseTimeout(Timeout.ofMilliseconds(this.readTimeoutMillis))
                .build();
        this.httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionReuseStrategy((request, response, context) -> false)
                .disableAutomaticRetries()
                .build();
    }

    public String getNameByGet(String name) {
        try {
            URIBuilder uriBuilder = new URIBuilder(buildUrl("/api/name/"));
            uriBuilder.addParameter("name", name);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            return executeRequest(httpGet, "");
        } catch (URISyntaxException e) {
            throw new OpenApiClientException("Failed to build request URI", e);
        }
    }

    private HashMap<String, String> getHeaderMap(String body) {
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("accessKey", accessKey);
        headerMap.put("nonce", String.valueOf(RandomUtil.randomInt(1000, 10000)));
        headerMap.put("body", body);
        headerMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        headerMap.put("sign", genSign(body, secretKey));
        return headerMap;
    }


    public String getUserNameByPost(User user) {
        String requestBody = JSONUtil.toJsonStr(user);
        HttpPost httpPost = new HttpPost(buildUrl("/api/name/user"));
        httpPost.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
        return executeRequest(httpPost, requestBody);
    }

    private String buildUrl(String path) {
        return gatewayHost + path;
    }

    private String executeRequest(ClassicHttpRequest request, String body) {
        request.setHeader(HttpHeaders.CONNECTION, "close");
        request.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        getHeaderMap(body).forEach(request::setHeader);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getCode();
            String responseBody = response.getEntity() == null
                    ? ""
                    : EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            if (statusCode < 200 || statusCode >= 300) {
                throw new OpenApiClientException("Gateway request failed with status " + statusCode, statusCode, responseBody);
            }
            return responseBody;
        } catch (IOException | ParseException e) {
            throw new OpenApiClientException("Gateway request failed", e);
        }
    }
}
