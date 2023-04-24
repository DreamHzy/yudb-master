package com.ynzyq.yudbadmin.dao.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SystemLogListVO {


    @ApiModelProperty("请求地址")
    private String url;

    @ApiModelProperty("请求参数信息")
    private String parameter;

    @ApiModelProperty("操作描述")
    private String description;

    @ApiModelProperty("操作时间")
    private Date createTime;

    @ApiModelProperty("请求IP")
    private String ip;


    @ApiModelProperty("返回信息")
    private String operteResult;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户名称")
    private String username;
}
