package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MerchantEditDto {


    private String id;

    @ApiModelProperty("客户名称")
    private String name;

//    @ApiModelProperty("省份")
//    private String province;
//
//    @ApiModelProperty("城市")
//    private String city;
//
//    @ApiModelProperty("详细地址")
//    private String address;

    @ApiModelProperty("是否为代理：1代理 2不是代理")
    private String isAgent;

    @ApiModelProperty("身份证/统一信用代码")
    private String idNumber;

    @ApiModelProperty("手机号")
    private String phone;
}
