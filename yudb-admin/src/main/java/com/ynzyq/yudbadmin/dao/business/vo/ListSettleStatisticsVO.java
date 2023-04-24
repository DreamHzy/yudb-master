package com.ynzyq.yudbadmin.dao.business.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/16 11:51
 * @description:
 */
@Data
public class ListSettleStatisticsVO implements Serializable {
    @ExcelIgnore
    @ApiModelProperty("结算id")
    private String statisticsId;

    @ExcelProperty
    @ApiModelProperty("账单日")
    private String statisticsDate;

    @ExcelProperty
    @ApiModelProperty("门店授权号")
    private String storeUid;

    @ExcelIgnore
    @ApiModelProperty("结算状态")
    private String settleStatus;

    @ExcelProperty
    @ApiModelProperty("结算状态文本")
    private String settleStatusDesc;

    @ExcelProperty
    @ApiModelProperty("钱包id")
    private String walletId;

    @ExcelProperty
    @ApiModelProperty("应结算金额")
    private String orderMoney;

    @ExcelProperty
    @ApiModelProperty("手续费")
    private String orderFee;

    @ExcelProperty
    @ApiModelProperty("已结算金额")
    private String settleMoney;

    @ExcelProperty
    @ApiModelProperty("未结算金额")
    private String notSettleMoney;

    @ExcelProperty
    @ApiModelProperty("结算时间")
    private String settleTime;

    @ExcelProperty
    @ApiModelProperty("操作人")
    private String operator;
}
