package com.yupi.openapi.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.openapi.common.ErrorCode;
import com.yupi.openapi.constant.CommonConstant;
import com.yupi.openapi.exception.BusinessException;
import com.yupi.openapi.exception.ThrowUtils;
import com.yupi.openapi.mapper.InterfaceInfoMapper;
import com.yupi.openapi.model.dto.interfaceinfo.InterfaceInfoQueryRequest;
import com.yupi.openapi.model.dto.post.PostQueryRequest;
import com.yupi.openapi.model.entity.InterfaceInfo;
import com.yupi.openapi.model.entity.Post;
import com.yupi.openapi.service.InterfaceInfoService;
import com.yupi.openapi.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface info service implementation.
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        String requestParams = interfaceInfo.getRequestParams();

        // When create for the first time, parameters must not be blank.
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name, requestParams), ErrorCode.PARAMS_ERROR);
        }

        // When name is not blank, the length must not be greater than 80.
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Title length must not be less than 50");
        }

        if (requestParams != null && StringUtils.isBlank(requestParams)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Request params can not be blank");
        }

    }

//    @Override
//    public QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
//        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
//        if (interfaceInfoQueryRequest == null) {
//            return queryWrapper;
//        }
//
//        String searchText = interfaceInfoQueryRequest.getSearchText();
//        String sortField = interfaceInfoQueryRequest.getSortField();
//        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
//        Long id = interfaceInfoQueryRequest.getId();
//        String title = interfaceInfoQueryRequest.getTitle();
//        String content = interfaceInfoQueryRequest.getContent();
//        List<String> tagList = interfaceInfoQueryRequest.getTags();
//        Long userId = interfaceInfoQueryRequest.getUserId();
//        Long notId = interfaceInfoQueryRequest.getNotId();
//        // 拼接查询条件
//        if (StringUtils.isNotBlank(searchText)) {
//            queryWrapper.and(qw -> qw.like("title", searchText).or().like("content", searchText));
//        }
//        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
//        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
//        if (CollUtil.isNotEmpty(tagList)) {
//            for (String tag : tagList) {
//                queryWrapper.like("tags", "\"" + tag + "\"");
//            }
//        }
//        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
//        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
//        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
//        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
//                sortField);
//        return queryWrapper;
//    }

}
