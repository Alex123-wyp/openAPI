package com.yupeng.openapi.controller;
import com.yupeng.openapi.annotation.AuthCheck;
import com.yupeng.openapi.common.BaseResponse;
import com.yupeng.openapi.common.ErrorCode;
import com.yupeng.openapi.exception.BusinessException;
import com.yupeng.openapi.exception.ThrowUtils;
import com.yupeng.openapi.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.yupeng.openapi.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest;
import com.yupeng.openapi.model.entity.UserInterfaceInfo;
import com.yupeng.openapi.service.UserInterfaceInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import static com.yupeng.openapi.common.ResultUtils.success;

@RestController
@RequestMapping
public class UserInterfaceController {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * Add user interface info
     */
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Long> addUserInterfaceInfo(@RequestBody UserInterfaceInfoAddRequest userInterfaceInfoAddRequest){
        if(userInterfaceInfoAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoAddRequest, userInterfaceInfo);
        boolean add = true;
        userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo, add);
        boolean result = userInterfaceInfoService.save(userInterfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.SYSTEM_ERROR);
        long newUserInterfaceInfoId = userInterfaceInfo.getId();
        return success(newUserInterfaceInfoId);
    }

    /**
     * Query user interface info
     */
    @AuthCheck(mustRole = "admin")
    public BaseResponse<UserInterfaceInfo> getUserInterfaceinfoByUserId(@RequestParam Long id){
        if (id == null || id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo newUserInterfaceInfo = new UserInterfaceInfo();
        newUserInterfaceInfo = userInterfaceInfoService.getById(id);
        if(newUserInterfaceInfo == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return success(newUserInterfaceInfo);
    }

    /**
     * Update user interface info
     */

    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> updateUserInterfaceInfo(@RequestBody UserInterfaceInfoUpdateRequest userInterfaceInfoUpdateRequest){
        if(userInterfaceInfoUpdateRequest == null || userInterfaceInfoUpdateRequest.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoUpdateRequest, userInterfaceInfo);
        UserInterfaceInfo oldInterfaceInfo = userInterfaceInfoService.getById(userInterfaceInfo.getId());
        if(userInterfaceInfo.getTotalNum() < oldInterfaceInfo.getTotalNum()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Total Number can not be less than left number!");
        }
        boolean result = userInterfaceInfoService.updateById(userInterfaceInfo);
        ThrowUtils.throwIf(!result, new BusinessException(ErrorCode.SYSTEM_ERROR));
        return success(result);
    }

}
