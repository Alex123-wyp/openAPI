package com.yupeng.openapi.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * Update request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {


    private Long id;
    /**
     * Interface id.
     */
//    private Long interfaceInfoId;

    /**open_api
     * Total invocation count.
     */
    private Integer totalNum;
    /**
     * Remaining invocation count.
     */
//    private Integer leftNum;
    /**
     * Status: 0-active, 1-disabled.
     */
    private Integer status;


}
