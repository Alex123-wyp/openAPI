package com.yupeng.openapi.model.vo;


import lombok.Data;

@Data
public class ListTopInvokerInterfaceInfoVO {
    /**
     * Interface id.
     */
    private Long interfaceInfoId;

    /**
     * Total invocation count.
     */
    private Integer totalNum;

}
