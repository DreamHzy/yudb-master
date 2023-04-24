package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MerchantPlatformPageVo {

    private String id;

    @ApiModelProperty("加盟商")
    private String merchantName;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty(value = "费用类型")
    private String payTypeName;

    @ApiModelProperty(value = "金额")
    private String money;

    @ApiModelProperty(value = "已缴金额")
    private String payMoney;

    @ApiModelProperty(value = "剩余应缴金额")
    private String remain;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "订单状态1、待支付")
    private String status;

    @ApiModelProperty(value = "缴费截止时间")
    private String expireTime;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "角色类型")
    private String roleTypeName;

    @ApiModelProperty("调整次数")
    private String adjustmentCount;

}
