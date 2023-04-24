package com.ynzyq.yudbadmin.dao.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("系统操作日志")
public class SystemLog {
    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;

    @ApiModelProperty(value = "请求IP")
    private String url;

    @ApiModelProperty(value = "请求参数信息")
    private String parameter;

    @ApiModelProperty(value = "操作描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建者ID", example = "1")
    private Integer createUser;

    @ApiModelProperty(value = "请求IP")
    private String ip;
    @ApiModelProperty(value = "返回信息")
    private String operteResult;
}