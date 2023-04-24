package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaymentRecordMerchantVo {

    private String id;

    @ApiModelProperty("加盟商")
    private String merchantName;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("费用类型")
    private String payTypeName;

    @ApiModelProperty("金额")
    private String money;

    @ApiModelProperty(value = "已缴金额")
    private String payMoney;

    @ApiModelProperty(value = "剩余应缴金额")
    private String remain;

    @ApiModelProperty("支付状态 2支付成功")
    private String status;

    @ApiModelProperty("生成时间")
    private String createTime;

    @ApiModelProperty("支付时间")
    private String payTime;

    @ApiModelProperty("支付时间")
    private String expireTime;
}
