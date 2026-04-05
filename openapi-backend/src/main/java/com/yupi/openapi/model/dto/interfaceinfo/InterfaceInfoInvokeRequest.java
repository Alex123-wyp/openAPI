package com.yupi.openapi.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * Interface information invoke request
 *
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    /**
     * id
     */
    private Long id;



    private String userRequestParams;

    private String requestHeader;

    private String responseHeader;

    private Integer status;
}
