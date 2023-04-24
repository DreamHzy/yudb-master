package com.ynzyq.yudbadmin.third.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/15 19:23
 * @description:
 */
@Data
public class TransferOrderDTO implements Serializable {

    @ApiModelProperty("订单编号")
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty("门店授权号")
    @NotBlank(message = "门店授权号不能为空")
    private String storeUid;

    @ApiModelProperty("订单金额")
    @NotBlank(message = "订单金额不能为空")
    private String orderMoney;

    @ApiModelProperty("订单时间")
    @NotBlank(message = "订单时间不能为空")
    private String orderTime;

    @ApiModelProperty("订单手续费")
    @NotBlank(message = "订单手续费不能为空")
    private String orderFee;

    @ApiModelProperty("时间戳毫秒数")
    @NotBlank(message = "时间戳毫秒数不能为空")
    private String timestamp;

    @ApiModelProperty("签名")
    @NotBlank(message = "签名不能为空")
    private String sign;
}
