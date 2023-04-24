package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WJ
 */
@Data
public class MerchantPlatformAgentVO implements Serializable {

    @ApiModelProperty("订单id")
    private String id;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty(value = "费用类型")
    private String payTypeName;

    @ApiModelProperty(value = "金额")
    private String money;

    @ApiModelProperty(value = "已缴金额")
    private String payMoney;

    @ApiModelProperty(value = "剩余应缴金额")
    private String remain;

    @ApiModelProperty(value = "订单状态：1、待支付 2、支付成功 3、取消 4、审核拒绝")
    private String status;

    @ApiModelProperty(value = "缴费截止时间")
    private String expireTime;


    @ApiModelProperty(value = "手机号")
    private String mobile;


}
