package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeliveryVO implements Serializable {

    @ApiModelProperty("收货人")
    private String receiver;

    @ApiModelProperty("收货电话")
    private String receiverMobile;

    private String province;

    private String city;

    private String area;

    @ApiModelProperty("收货省市区")
    private String provinceCityDistrict;

    @ApiModelProperty("收货详细地址")
    private String deliveryAddress;
}
