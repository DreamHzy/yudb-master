package com.ynzyq.yudbadmin.third.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserInfoDTO implements Serializable {

    @ApiModelProperty("用户类型 1门店  2代理权")
    @NotBlank(message = "类型不能为空")
    private String userType;

    @ApiModelProperty("门店授权号/代理权授权号")
    @NotBlank(message = "门店授权号/代理权授权号不能为空")
    private String uid;

    @ApiModelProperty("操作类型 1新增 2修改")
    @NotBlank(message = "操作类型不能为空")
    private String type;

    public UserInfoDTO(String userType, String uid, String type) {
        this.userType = userType;
        this.uid = uid;
        this.type = type;
    }
}
