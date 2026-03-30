package com.yupi.openapi.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class InterfaceInfoUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * name
     */
    private String name;

    private String description;

    private String url;

    private String method;

    private String requestHeader;

    private String responseHeader;

    private Integer status;

}