package com.yupi.openapi.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
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
