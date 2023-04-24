package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PlatformPageDetatilVo {
    @ApiModelProperty("订单编号")
    private String id;


    @ApiModelProperty("加盟商名称")
    private String merchantName;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty(value = "费用类型")
    private String payTypeName;

    @ApiModelProperty(value = "金额")
    private String money;


    @ApiModelProperty(value = "订单状态 2已支付 ")
    private String status;

    @ApiModelProperty(value = "生成时间")
    private Date createTime;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;


    @ApiModelProperty(value = "下部分标黑字体")
    private String remarkOne;


    @ApiModelProperty(value = "下部分常规字体")
    private String remarkTwo;


}
