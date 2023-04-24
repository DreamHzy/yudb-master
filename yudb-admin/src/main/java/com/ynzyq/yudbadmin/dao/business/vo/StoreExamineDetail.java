package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/27 1:06
 * @description:
 */
@Data
public class StoreExamineDetail implements Serializable {

    @ApiModelProperty("审批单id")
    private String id;

    @ApiModelProperty("申请人")
    private String applyName;

    @ApiModelProperty("授权号")
    private String merchantStoreUid;

    @ApiModelProperty("加盟商")
    private String merchantName;

//    @ApiModelProperty("门店地址")
//    private String address;

    @ApiModelProperty("审核类型 1、金额调整 2、延期支付 3、新订单审核 4、取消费用")
    private String examineType;

//    @ApiModelProperty("费用类型id")
//    private String paymentTypeId;

    @ApiModelProperty("费用类型")
    private String paymentTypeName;

    @ApiModelProperty("金额")
    private String money;

//    @ApiModelProperty("阶段审核人")
//    private String approveName;

//    @ApiModelProperty("状态：1、审核中 2、通过 3、拒绝")
//    private String status;

    @ApiModelProperty("具体内容")
    private String msg;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("申请时间")
    private String createTime;

    @ApiModelProperty("缴费截止时间")
    private String expireTime;
}
