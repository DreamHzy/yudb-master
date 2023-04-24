package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/2/14 16:05
 * @description:
 */
@Data
public class UpdateDeliveryAddressDTO implements Serializable {
    @ApiModelProperty("门店id")
    @NotBlank(message = "门店id不能为空")
    private String storeId;

    @ApiModelProperty("送货地址")
    @NotBlank(message = "送货地址不能为空")
    private String deliveryAddress;

    @ApiModelProperty("收货人")
    @NotBlank(message = "收货人不能为空")
    private String receiver;

    @ApiModelProperty("收货电话")
    @NotBlank(message = "收货电话不能为空")
    private String receiverMobile;

    @ApiModelProperty("是否显示收货地址：1：是，2：否")
    @NotBlank(message = "是否显示收货地址不能为空")
    private String showDeliveryAddress;

    @ApiModelProperty("运费省份")
    @NotBlank(message = "运费省份不能为空")
    private String deliveryProvince;

    @ApiModelProperty("运费市区")
    @NotBlank(message = "运费市区不能为空")
    private String deliveryCity;

    @ApiModelProperty("运费区县")
    @NotBlank(message = "运费区县不能为空")
    private String deliveryArea;
}
