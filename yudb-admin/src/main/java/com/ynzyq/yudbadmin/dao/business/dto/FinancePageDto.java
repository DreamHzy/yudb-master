package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FinancePageDto {


    @ApiModelProperty(value = "关键字搜索")
    private String condition;

    @ApiModelProperty(value = "缴费开始时间")
    private String startexpireTime;

    @ApiModelProperty(value = "缴费截止时间")
    private String endexpireTime;


    @ApiModelProperty(value = "支付开始时间")
    private String startPayTime;

    @ApiModelProperty(value = "支付截止时间")
    private String endPayTime;

    @ApiModelProperty(value = "缴费类型标识")
    private String paymentTypeId;

    @ApiModelProperty(value = "1未支付 2已支付")
    private String payStatus;

    @ApiModelProperty(value = "付款类型")
    private String payType;

    @ApiModelProperty(value = "是否推送 1推送给加盟商 2未推送给加盟商")
    private String send;

    private Integer status;

    @ApiModelProperty("缴费类型id")
    private String typeId;

    @ApiModelProperty("区域经理id")
    private String managerId;

    @ApiModelProperty("订单id")
    private String orderId;

    @ApiModelProperty("是否发布：1：已发布，2：未发布")
    private String examine;

    @ApiModelProperty("收款月份")
    private String month;

    @ApiModelProperty("一级状态")
    private String state;

    @ApiModelProperty(value = "订单创建开始时间")
    private String startCreateTime;

    @ApiModelProperty(value = "订单创建结束时间")
    private String endCreateTime;


}
