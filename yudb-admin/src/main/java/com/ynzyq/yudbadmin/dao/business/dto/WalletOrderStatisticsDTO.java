package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xinchen
 * @date 2022/6/17 14:02
 * @description:
 */
@Data
public class WalletOrderStatisticsDTO {
    @ApiModelProperty("日期")
    private Date orderDate;

    @ApiModelProperty("门店授权号")
    private String storeUid;

    @ApiModelProperty("订单金额")
    private BigDecimal orderMoney;

    @ApiModelProperty("订单手续费")
    private BigDecimal orderFee;

    @ApiModelProperty("钱包id")
    private String walletId;
}
