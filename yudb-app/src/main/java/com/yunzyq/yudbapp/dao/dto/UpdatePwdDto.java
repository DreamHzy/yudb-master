package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePwdDto {

    @ApiModelProperty("新密码(加密后的)")
    private String newPwd;

    @ApiModelProperty("旧密码(加密后的)")
    private String oldPwd;
}
