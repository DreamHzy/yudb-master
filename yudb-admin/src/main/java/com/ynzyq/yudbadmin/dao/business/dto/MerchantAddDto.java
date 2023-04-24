package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MerchantAddDto {

    @ApiModelProperty("加盟商名称")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("身份证号")
    private String idNumber;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("是否代理 1代理 2不是代理")
    private String isAgent;

}
