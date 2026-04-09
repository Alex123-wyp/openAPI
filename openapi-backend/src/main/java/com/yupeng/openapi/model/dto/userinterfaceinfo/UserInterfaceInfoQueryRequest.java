package com.yupeng.openapi.model.dto.userinterfaceinfo;


import lombok.Data;
import org.springframework.data.querydsl.QPageRequest;

import java.io.Serializable;

@Data
public class UserInterfaceInfoQueryRequest implements Serializable {


    private Long id;

    /**
     * Interface id.
     */
    private Long interfaceInfoId;

    /**open_api
     * Total invocation count.
     */
    private Integer totalNum;

    /**
     * Remaining invocation count.
     */
    private Integer leftNum;

    /**
     * Status: 0-active, 1-disabled.
     */
    private Integer status;

}
