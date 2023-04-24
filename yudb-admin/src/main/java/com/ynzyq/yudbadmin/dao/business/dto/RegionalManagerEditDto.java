package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegionalManagerEditDto {

    @ApiModelProperty("区域经理id")
    private String id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("部门id")
    private String departmentId;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("员工编号")
    private String uid;
}
