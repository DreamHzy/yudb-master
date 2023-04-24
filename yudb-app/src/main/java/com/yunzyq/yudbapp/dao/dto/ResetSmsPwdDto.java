package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResetSmsPwdDto {

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("密码(aes cbc模式加密解密)")
    private String pwd;
}
