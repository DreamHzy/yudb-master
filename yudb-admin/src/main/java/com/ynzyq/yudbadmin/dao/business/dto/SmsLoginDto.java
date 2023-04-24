package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SmsLoginDto {

    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("验证码")
    private String code;
}
