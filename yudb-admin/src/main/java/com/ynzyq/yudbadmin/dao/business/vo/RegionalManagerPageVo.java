package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegionalManagerPageVo {

    private String id;
    @ApiModelProperty("员工编号")
    private String uid;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("手机号")
    private String mobile;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("门店数")
    private String sotreCount;
    @ApiModelProperty("创建人")
    private String createUser;
    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty(value = "部门")
    private String departmentName;
    @ApiModelProperty(value = "1启用2停用")
    private String status;

    private String departmentId;

    @ApiModelProperty(value = "代理权数")
    private String agentAreaCount;
}
