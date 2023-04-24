package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/16 16:43
 * @description:
 */
@Data
public class SettleStatisticsDetailVO implements Serializable {
    @ApiModelProperty("订单id")
    private String id;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("订单时间")
    private String orderTime;

    @ApiModelProperty("订单金额")
    private String orderMoney;

    @ApiModelProperty("订单手续费")
    private String orderFee;

    @ApiModelProperty("实际到账金额")
    private String actualMoney;

    @ApiModelProperty("结算状态：1：已完成，2：待结算，3：结算中，4：结算失败，5：已关闭")
    private String settleStatus;

    @ApiModelProperty("结算状态文本")
    private String settleStatusDesc;
}
