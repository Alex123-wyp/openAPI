package com.yupi.openapi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Interface info entity.
 */
@TableName(value = "interface_info")
@Data
public class InterfaceInfo implements Serializable {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("name")
    private String name;

    @ApiModelProperty("description")
    private String description;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("method")
    private String method;

    @ApiModelProperty("request header")
    private String requestHeader;

    @ApiModelProperty("response header")
    private String responseHeader;

    @ApiModelProperty("status")
    private Integer status;

    @ApiModelProperty("creator user id")
    private Long userId;

    @ApiModelProperty("created time")
    private Date createTime;

    @ApiModelProperty("updated time")
    private Date updateTime;

    @TableLogic
    @ApiModelProperty("is deleted")
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}
