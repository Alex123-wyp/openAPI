package com.yupeng.openapi.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * Update request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
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

    private String requestParams;

    private String requestHeader;

    private String responseHeader;

    private Integer status;
}
