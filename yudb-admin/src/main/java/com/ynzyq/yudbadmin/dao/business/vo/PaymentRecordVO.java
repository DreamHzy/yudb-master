package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Data
public class PaymentRecordVO implements Serializable {
    @ApiModelProperty("缴费类型")
    private String paymentTypeName;

    @ApiModelProperty("付款周期(开始时间~结束时间)")
    private String cycle;

    @ApiModelProperty("缴费标准")
    private String paymentStandardMoney;

    @ApiModelProperty("是否调整")
    private String isAdjust;

    @ApiModelProperty("缴费金额")
    private String money;

    @ApiModelProperty("已缴费金额")
    private String paymentAmount;

    @ApiModelProperty("缴费时间")
    private String payTime;

    @ApiModelProperty("调整内容")
    private String adjustmentMsg;

}
