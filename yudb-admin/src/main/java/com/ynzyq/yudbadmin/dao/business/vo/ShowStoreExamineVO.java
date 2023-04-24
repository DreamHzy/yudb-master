package com.ynzyq.yudbadmin.dao.business.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/27 1:06
 * @description:
 */
@Data
public class ShowStoreExamineVO implements Serializable {

    @ExcelIgnore
    @ApiModelProperty("审批单id")
    private String id;

    @ExcelProperty(value = "申请人")
    @ApiModelProperty("申请人")
    private String applyName;

    @ExcelProperty(value = "授权号")
    @ApiModelProperty("授权号")
    private String merchantStoreUid;

    @ExcelProperty(value = "门店地址")
    @ApiModelProperty("门店地址")
    private String address;

    @ExcelProperty(value = "审核类型")
    @ApiModelProperty("审核类型 1、金额调整 2、延期支付 3、新订单审核 4、取消费用")
    private String examineType;

    @ExcelIgnore
    @ApiModelProperty("费用类型id")
    private String paymentTypeId;

    @ExcelProperty(value = "费用类型")
    @ApiModelProperty("费用类型")
    private String paymentTypeName;

    @ExcelProperty(value = "金额")
    @ApiModelProperty("金额")
    private String money;

    @ExcelProperty(value = "阶段审核人")
    @ApiModelProperty("阶段审核人")
    private String approveName;

    @ExcelProperty(value = "状态")
    @ApiModelProperty("状态：1、审核中 2、通过 3、拒绝")
    private String status;

    @ExcelProperty(value = "申请时间")
    @ApiModelProperty("申请时间")
    private String createTime;

    @ExcelProperty(value = "缴费截止时间")
    @ApiModelProperty("缴费截止时间")
    private String expireTime;

    @ExcelProperty(value = "区域")
    @ApiModelProperty("区域")
    private String region;
}
