package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ModifyDataDTO implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("收货人")
    private String receipt;

    @ApiModelProperty("收货人电话")
    private String receiptPhone;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("收货人详细地址")
    private String receiptAddress;
}
