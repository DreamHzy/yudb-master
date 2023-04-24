package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegionalManagerAddDto {


    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("部门id")
    private String departmentId;

    @ApiModelProperty("员工编号")
    private String uid;
}
