package com.yupi.openapi.model.dto.interfaceinfo;

import com.yupi.openapi.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
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
