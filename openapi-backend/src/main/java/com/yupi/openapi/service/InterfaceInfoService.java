package com.yupi.openapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.openapi.model.entity.InterfaceInfo;
import com.yupi.openapi.model.entity.Post;

/**
 * Interface info service.
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

}


