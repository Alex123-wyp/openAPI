package com.yupeng.openapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupeng.openapi.model.entity.InterfaceInfo;

/**
 * Interface info service.
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    /**
     * Resolve the online interface row that matches the current public request.
     *
     * The gateway sends path + method instead of a fixed interface id, so the
     * backend must translate that request into the right interface_info row.
     */
    InterfaceInfo getOnlineInterfaceInfoByPathAndMethod(String requestPath, String requestMethod);

}

