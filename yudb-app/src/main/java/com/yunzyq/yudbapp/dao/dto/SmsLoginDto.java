package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SmsLoginDto {
    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("验证码")
    private String code;

    private String jsCode;
}
