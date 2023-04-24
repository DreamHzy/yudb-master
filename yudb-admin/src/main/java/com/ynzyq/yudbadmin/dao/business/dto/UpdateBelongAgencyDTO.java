package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateBelongAgencyDTO implements Serializable {

    @ApiModelProperty("门店id")
    private Integer storeId;

    @ApiModelProperty("代理id")
    private Integer proxyId;

}
