package com.yupi.openapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yupi.openapiclientsdk.modal.User;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import static com.yupi.openapiclientsdk.utils.SignUtils.genSign;

/**
 * Invoke 3-praty API client
 *
 * @Author yupewan(Alex Wang)
 */
public class OpenApiClient {

    public static final String DEFAULT_GATEWAY_HOST = "http://localhost:8123";

    private final String gatewayHost;
    private String accessKey;
    private String secretKey;


//    public OpenApiClient() {
//        this(DEFAULT_GATEWAY_HOST);
//    }

    public OpenApiClient(String gatewayHost, String accessKey, String secretKey) {
        this.gatewayHost = StrUtil.removeSuffix(gatewayHost, "/");
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.get(buildUrl("/api/name/"), paramMap);
    }

    private HashMap<String, String> getHeaderMap(String body){
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("accessKey", accessKey);
        //Never send secretKey in header or body, it's only used to generate sign
//        headerMap.put("secretKey", secretKey);
        headerMap.put("nonce", String.valueOf(RandomUtil.randomInt(1000, 10000)));
        headerMap.put("body", body);
        headerMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        headerMap.put("sign", genSign(body, secretKey));
        return headerMap;
    }


    public String getUserNameByPost(User user) {
        return   HttpRequest.post(buildUrl("/api/name/user"))
                .charset(StandardCharsets.UTF_8)
                .body(JSONUtil.toJsonStr(user), ContentType.JSON.getValue())
                .addHeaders(getHeaderMap(JSONUtil.toJsonStr(user)))
                .execute()
                .body();

    }

    private String buildUrl(String path) {
        return gatewayHost + path;
    }
}
