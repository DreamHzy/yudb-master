package com.ynzyq.yudbadmin.dao.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SiteListVo {

    private String id;

    @ApiModelProperty(value = "站点名称")
    private String siteName;


    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "地址")
    private String address;


    @ApiModelProperty(value = "合作状态 1未合作 2洽谈中 3已合作")
    private String cooperationStatus;


    @ApiModelProperty(value = "状态 1启用 2停用")
    private String status;

    @ApiModelProperty(value = "用户展示 1是  2不是")
    private String exhibit;

    @ApiModelProperty(value = "业务员")
    private String realname;

    @ApiModelProperty(value = "商家")
    private String name;

    private String createTime;

}
