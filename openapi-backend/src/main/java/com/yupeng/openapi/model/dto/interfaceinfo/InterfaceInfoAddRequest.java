package com.yupeng.openapi.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Create request
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
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
     * requestParams
     */
    private String requestParams;

    /**
     * requestHeader
     */
    private String requestHeader;

    /**
     * responseHeader
     */
    private String responseHeader;
}
