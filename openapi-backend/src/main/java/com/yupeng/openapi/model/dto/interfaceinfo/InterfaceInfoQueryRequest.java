package com.yupeng.openapi.model.dto.interfaceinfo;

import com.yupeng.openapi.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Query request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String url;

    private String method;

    private String requestParams;

    private String requestHeader;

    private String responseHeader;

    private Integer status;

    private Long userId;

    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}
