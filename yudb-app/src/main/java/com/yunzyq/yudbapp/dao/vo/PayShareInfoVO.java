package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/15 17:45
 * @description:
 */
@Data
public class PayShareInfoVO implements Serializable {
    @ApiModelProperty("订单id")
    private String id;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("加盟商")
    private String merchantName;

    @ApiModelProperty("缴费类型")
    private String paymentType;

    @ApiModelProperty("缴费金额")
    private String money;

    @ApiModelProperty(value = "已缴金额")
    private String payMoney;

    @ApiModelProperty(value = "剩余应缴金额")
    private String remain;

    @ApiModelProperty("支付状态")
    private String payStatus;
}
