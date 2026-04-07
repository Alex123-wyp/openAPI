package com.yupi.openapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.openapi.model.entity.InterfaceInfo;
import com.yupi.openapi.model.entity.UserInterfaceInfo;

/**
 * User interface relationship service.
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {



    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    boolean invokeCount(long interfaceInfoId, long userid);

}
