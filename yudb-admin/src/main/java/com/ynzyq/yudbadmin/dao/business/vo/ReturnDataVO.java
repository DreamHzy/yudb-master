package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReturnDataVO implements Serializable {

    @ApiModelProperty("收货人")
    private String receiver;

    @ApiModelProperty("收货电话")
    private String phone;

    private String receiptProvince;

    private String receiptCity;

    private String receiptArea;

    @ApiModelProperty("收货地址")
    private String address;

}
