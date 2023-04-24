package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVo implements Serializable {

    @ApiModelProperty("TOKEN")
    private String token;

    @ApiModelProperty("2加盟商 3区域经理")
    private String userType;

    /**
     * 是否为代理：0：否，1：是
     */
    @ApiModelProperty("是否为代理：0：否，1：是")
    private String isMerchant;
}
