package com.yupeng.openapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yupeng.openapi.model.entity.UserInterfaceInfo;

import java.util.List;

/**
 * User interface relationship database operations.
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);

}
