package com.yupeng.openapi.model.dto.userinterfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * Create request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {

    private Long id;

    /**
     * Interface id.
     */
    private Long interfaceInfoId;

    /**
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
