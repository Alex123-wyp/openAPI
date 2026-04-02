package com.yupi.openapipublicinterface.client;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yupi.openapipublicinterface.modal.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Invoke 3-praty API client
 *
 * @Author yupewan(Alex Wang)
 */
public class OpenApiClient {

    private static final String DEFAULT_GATEWAY_HOST = "http://localhost:8123";

    private final String gatewayHost;

    public OpenApiClient() {
        this(DEFAULT_GATEWAY_HOST);
    }

    public OpenApiClient(String gatewayHost) {
        this.gatewayHost = StrUtil.removeSuffix(gatewayHost, "/");
    }

    public String getNameByGet(String name) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.get(buildUrl("/api/name/"), paramMap);
    }

    public String getNameByPost(String name) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.post(buildUrl("/api/name/"), paramMap);
    }

    public String getUserNameByPost(User user) {
        return   HttpRequest.post(buildUrl("/api/name/user"))
                .body(JSONUtil.toJsonStr(user), ContentType.JSON.getValue())
                .execute()
                .body();

    }

    private String buildUrl(String path) {
        return gatewayHost + path;
    }
}
