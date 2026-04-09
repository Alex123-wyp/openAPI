package com.yupeng.openapi.controller;
import com.yupeng.openapi.annotation.AuthCheck;
import com.yupeng.openapi.common.BaseResponse;
import com.yupeng.openapi.common.ResultUtils;
import com.yupeng.openapi.mapper.UserInterfaceInfoMapper;
import com.yupeng.openapi.model.entity.UserInterfaceInfo;
import com.yupeng.openapi.model.vo.ListTopInvokerInterfaceInfoVO;
import com.yupeng.openapi.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<ListTopInvokerInterfaceInfoVO>> listTopInvokerInterfaceInfo(){
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        List<ListTopInvokerInterfaceInfoVO> infoVOList = userInterfaceInfoList.stream().map(userInterfaceInfo ->
        {
            ListTopInvokerInterfaceInfoVO listTopInvokerInterfaceInfoVO = new ListTopInvokerInterfaceInfoVO();
            BeanUtils.copyProperties(userInterfaceInfo, listTopInvokerInterfaceInfoVO);
            return listTopInvokerInterfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(infoVOList);
    }



}