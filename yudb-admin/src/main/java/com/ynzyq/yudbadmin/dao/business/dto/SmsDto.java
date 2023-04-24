package com.ynzyq.yudbadmin.dao.business.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SmsDto {

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("类型 LOGIN登录   PWD重置密码")
    private String type;

}
