package com.yupi.openapi.model.dto.userinterfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
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
