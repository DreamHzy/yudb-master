package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MerchantIdDTO implements Serializable {

    @ApiModelProperty(value = "代理权id")
    private String merchantId;

    @ApiModelProperty(value = "收款账户名称")
    private String accountName;

    @ApiModelProperty(value = "收款账户号")
    private String publicAccount;

    @ApiModelProperty(value = "开户支行")
    private String bank;
}
