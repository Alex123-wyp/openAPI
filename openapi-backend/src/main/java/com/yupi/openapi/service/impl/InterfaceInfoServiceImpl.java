package com.yupi.openapi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.openapi.mapper.InterfaceInfoMapper;
import com.yupi.openapi.model.entity.InterfaceInfo;
import com.yupi.openapi.service.InterfaceInfoService;
import org.springframework.stereotype.Service;

/**
 * Interface info service implementation.
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

}
