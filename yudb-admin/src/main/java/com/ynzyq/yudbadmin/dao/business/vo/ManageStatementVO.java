package com.ynzyq.yudbadmin.dao.business.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/14 16:58
 * @description:
 */
@Data
public class ManageStatementVO implements Serializable {
    @ExcelProperty(value = "订单类型")
    @ApiModelProperty("订单类型")
    private String type;

    @ExcelProperty(value = "区域")
    @ApiModelProperty("区域")
    private String region;

    @ApiModelProperty("区域经理id")
    private String managerId;

    @ExcelProperty(value = "区域经理")
    @ApiModelProperty("区域经理")
    private String managerName;

    @ApiModelProperty("缴费类型id")
    private String paymentTypeId;

    @ExcelProperty(value = "缴费类型")
    @ApiModelProperty("缴费类型")
    private String paymentTypeName;

    @ExcelProperty(value = "应缴费单数")
    @ApiModelProperty("应缴费单数")
    private String count;

    @ExcelProperty(value = "已缴费单数")
    @ApiModelProperty("已缴费单数")
    private String payCount;

    @ExcelProperty(value = "未缴费单数")
    @ApiModelProperty("未缴费单数")
    private String notPayCount;

    @ExcelProperty(value = "应缴费金额")
    @ApiModelProperty("应缴费金额")
    private String amount;

    @ExcelProperty(value = "已缴费金额")
    @ApiModelProperty("已缴费金额")
    private String payAmount;

    @ExcelProperty(value = "未缴费金额")
    @ApiModelProperty("未缴费金额")
    private String notPayAmount;
}
