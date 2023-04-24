package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateMerchantDTO implements Serializable {
    @ApiModelProperty("门店id")
    private String  storeId;

    @ApiModelProperty("代理商id")
    private String  merchantId;
}
