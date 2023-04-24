package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MonitorUserAddDto {

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("姓名")
    private String name;
}
