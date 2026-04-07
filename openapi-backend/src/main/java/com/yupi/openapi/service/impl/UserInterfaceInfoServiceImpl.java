package com.yupi.openapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.openapi.common.ErrorCode;
import com.yupi.openapi.exception.BusinessException;
import com.yupi.openapi.exception.ThrowUtils;
import com.yupi.openapi.mapper.UserInterfaceInfoMapper;
import com.yupi.openapi.model.entity.InterfaceInfo;
import com.yupi.openapi.model.entity.UserInterfaceInfo;
import com.yupi.openapi.service.UserInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * User interface relationship service implementation.
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfonterfaceInfo, boolean add) {
        if (userInterfaceInfonterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // When create for the first time, parameters must not be blank.
        if (add) {

            if(userInterfaceInfonterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfonterfaceInfo.getUserId() <= 0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "Interface or user does not exist!");
            }

            if(userInterfaceInfonterfaceInfo.getLeftNum() < 0){
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "Left num can not be less than 0!");
            }
        }




    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        if(interfaceInfoId <= 0 || userId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
        updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        //This mean the current object instance
        return update(updateWrapper);
    }


}
