package com.yupeng.openapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupeng.openapi.common.ErrorCode;
import com.yupeng.openapi.exception.BusinessException;
import com.yupeng.openapi.mapper.UserInterfaceInfoMapper;
import com.yupeng.openapi.model.entity.UserInterfaceInfo;
import com.yupeng.openapi.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

/**
 * User interface relationship service implementation.
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    private static final int ACTIVE_STATUS = 0;
    private static final int NOT_DELETED = 0;

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // When create for the first time, parameters must not be blank.
        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "Interface or user does not exist!");
            }
            if (userInterfaceInfo.getLeftNum() < 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "Left num can not be less than 0!");
            }
        }
    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        // Keep the old API available for compatibility, but implement it on top
        // of the new reserve / commit model so it follows the same semantics.
        boolean reserved = reserveInvokeQuota(interfaceInfoId, userId);
        if (!reserved) {
            return false;
        }
        boolean committed = commitInvokeQuota(interfaceInfoId, userId);
        if (!committed) {
            rollbackInvokeQuota(interfaceInfoId, userId);
        }
        return committed;
    }

    @Override
    public boolean reserveInvokeQuota(long interfaceInfoId, long userId) {
        validateIds(interfaceInfoId, userId);
        UpdateWrapper<UserInterfaceInfo> updateWrapper = baseQuotaWrapper(interfaceInfoId, userId);
        // This single SQL update is the concurrency-safe reserve step:
        // only rows with leftNum > 0 can decrement by 1.
        updateWrapper.gt("leftNum", 0);
        updateWrapper.setSql("leftNum = leftNum - 1");
        return update(updateWrapper);
    }

    @Override
    public boolean commitInvokeQuota(long interfaceInfoId, long userId) {
        validateIds(interfaceInfoId, userId);
        UpdateWrapper<UserInterfaceInfo> updateWrapper = baseQuotaWrapper(interfaceInfoId, userId);
        // Commit only records a successful invoke in totalNum.
        updateWrapper.setSql("totalNum = totalNum + 1");
        return update(updateWrapper);
    }

    @Override
    public boolean rollbackInvokeQuota(long interfaceInfoId, long userId) {
        validateIds(interfaceInfoId, userId);
        UpdateWrapper<UserInterfaceInfo> updateWrapper = baseQuotaWrapper(interfaceInfoId, userId);
        // Rollback gives one reserved slot back to the caller.
        updateWrapper.setSql("leftNum = leftNum + 1");
        return update(updateWrapper);
    }

    private UpdateWrapper<UserInterfaceInfo> baseQuotaWrapper(long interfaceInfoId, long userId) {
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
        updateWrapper.eq("status", ACTIVE_STATUS);
        updateWrapper.eq("isDelete", NOT_DELETED);
        return updateWrapper;
    }

    private void validateIds(long interfaceInfoId, long userId) {
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
}
