package com.yupeng.openapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupeng.openapi.common.ErrorCode;
import com.yupeng.openapi.exception.BusinessException;
import com.yupeng.openapi.exception.ThrowUtils;
import com.yupeng.openapi.mapper.InterfaceInfoMapper;
import com.yupeng.openapi.model.entity.InterfaceInfo;
import com.yupeng.openapi.model.enums.InterfaceInfoStatusEnum;
import com.yupeng.openapi.service.InterfaceInfoService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Interface info service implementation.
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        String requestParams = interfaceInfo.getRequestParams();

        // When create for the first time, parameters must not be blank.
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name, requestParams), ErrorCode.PARAMS_ERROR);
        }

        // When name is not blank, the length must not be greater than 80.
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Title length must not be less than 50");
        }

        if (requestParams != null && StringUtils.isBlank(requestParams)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Request params can not be blank");
        }
    }

    @Override
    public InterfaceInfo getOnlineInterfaceInfoByPathAndMethod(String requestPath, String requestMethod) {
        if (StringUtils.isAnyBlank(requestPath, requestMethod)) {
            return null;
        }
        String normalizedRequestPath = normalizePath(requestPath);
        String normalizedRequestMethod = requestMethod.trim().toUpperCase(Locale.ROOT);

        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "url", "method", "status", "isDelete");
        queryWrapper.eq("status", InterfaceInfoStatusEnum.ONLINE.getValue());
        // Table logic will already filter deleted rows, but keeping the intent
        // explicit makes this quota path easier to read.
        queryWrapper.eq("isDelete", 0);
        List<InterfaceInfo> interfaceInfoList = list(queryWrapper);
        for (InterfaceInfo interfaceInfo : interfaceInfoList) {
            String normalizedInterfacePath = normalizePath(interfaceInfo.getUrl());
            String normalizedInterfaceMethod = normalizeMethod(interfaceInfo.getMethod());
            if (normalizedRequestPath.equals(normalizedInterfacePath)
                    && normalizedRequestMethod.equals(normalizedInterfaceMethod)) {
                return interfaceInfo;
            }
        }
        return null;
    }

    private String normalizeMethod(String requestMethod) {
        if (StringUtils.isBlank(requestMethod)) {
            return "";
        }
        return requestMethod.trim().toUpperCase(Locale.ROOT);
    }

    /**
     * Convert either:
     * - a full stored URL like http://localhost:8123/api/name/user
     * - or a relative path like /api/name/user
     * into the normalized comparable path only.
     */
    private String normalizePath(String urlOrPath) {
        if (StringUtils.isBlank(urlOrPath)) {
            return "";
        }
        String trimmedValue = urlOrPath.trim();
        try {
            URI uri = new URI(trimmedValue);
            // Full URLs have a scheme such as http / https.
            if (uri.getScheme() != null) {
                return normalizePathValue(uri.getPath());
            }
        } catch (URISyntaxException e) {
            // Fall back to plain path normalization below.
        }
        return normalizePathValue(trimmedValue);
    }

    private String normalizePathValue(String pathValue) {
        if (StringUtils.isBlank(pathValue)) {
            return "/";
        }
        String normalizedPath = pathValue.trim();
        int queryIndex = normalizedPath.indexOf('?');
        if (queryIndex >= 0) {
            normalizedPath = normalizedPath.substring(0, queryIndex);
        }
        if (!normalizedPath.startsWith("/")) {
            normalizedPath = "/" + normalizedPath;
        }
        return normalizedPath;
    }
}
