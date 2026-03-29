package com.yupi.openapi.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {

    /**
     * name
     */
    private String name;

    /**
     * description
     */
    private String description;

    /**
     * url
     */
    private String url;

    /**
     * method
     */
    private String method;

    /**
     * requestHeader
     */
    private String requestHeader;

    /**
     * responseHeader
     */
    private String responseHeader;


}