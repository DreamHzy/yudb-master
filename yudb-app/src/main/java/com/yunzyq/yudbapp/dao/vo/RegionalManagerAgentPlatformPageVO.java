package com.yunzyq.yudbapp.dao.vo;

import com.yunzyq.yudbapp.dao.dto.ExamineDetailDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RegionalManagerAgentPlatformPageVO implements Serializable {

    private String id;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("加盟商名称")
    private String merchantName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "费用类型")
    private String payTypeName;

    @ApiModelProperty(value = "金额")
    private String money;

    @ApiModelProperty(value = "已缴费金额")
    private String payMoney;

    @ApiModelProperty(value = "剩余缴费金额")
    private String remain;

    @ApiModelProperty(value = "订单状态1、待支付 2、支付成功 3、取消 4、审核拒绝")
    private String status;

    @ApiModelProperty(value = "是否推送")
    private String send;

    @ApiModelProperty(value = "付款截止时间")
    private String expireTime;


    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "审核状态1、未审核 2、审核中")
    private String examineStatus;

    @ApiModelProperty(value = "调整内容")
    private String adjustment;

    private String approveName;

    @ApiModelProperty(value = "调整次数")
    private String adjustmentCount;

    @ApiModelProperty(value = "申请时间")
    private String createTime;

    @ApiModelProperty("具体内容")
    private String msg;

    @ApiModelProperty("备注")
    private String remark;

    private String deleted;

    @ApiModelProperty("调整类型：1、金额调整 2、延期支付 3、新订单审核 4、取消费用，5：线下支付")
    private String examineType;

    @ApiModelProperty("审核信息")
    private List<ExamineDetailDTO> examineDetailList;
}
