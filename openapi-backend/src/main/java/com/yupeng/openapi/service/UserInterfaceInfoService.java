package com.yupeng.openapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupeng.openapi.model.entity.UserInterfaceInfo;

/**
 * User interface relationship service.
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * Compatibility method kept for older callers.
     * New gateway quota flow should use reserve + commit / rollback instead.
     */
    boolean invokeCount(long interfaceInfoId, long userid);

    /**
     * Reserve one available invocation slot before forwarding the request.
     */
    boolean reserveInvokeQuota(long interfaceInfoId, long userId);

    /**
     * Persist a successful invocation after the downstream API returns 2xx.
     */
    boolean commitInvokeQuota(long interfaceInfoId, long userId);

    /**
     * Give the reserved slot back when the downstream API fails.
     */
    boolean rollbackInvokeQuota(long interfaceInfoId, long userId);

}
