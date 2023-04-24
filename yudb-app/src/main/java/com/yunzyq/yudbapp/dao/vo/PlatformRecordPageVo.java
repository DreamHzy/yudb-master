package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlatformRecordPageVo {

    private String id;

    @ApiModelProperty("加盟商名称")
    private String merchantName;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty(value = "费用类型")
    private String payTypeName;

    @ApiModelProperty(value = "金额")
    private String money;


    @ApiModelProperty(value = "订单状态2已支付 ")
    private String status;

    @ApiModelProperty(value = "生成时间")
    private String createTime;

    @ApiModelProperty(value = "支付时间")
    private String payTime;


}
