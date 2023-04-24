package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegionalManagerDaibVo {


    @ApiModelProperty(value = "加盟商")
    private String merchantName;


    @ApiModelProperty(value = "授权号")
    private String uid;

    @ApiModelProperty("缴费金额")
    private String money;

    @ApiModelProperty("收款类型")
    private String payTypeName;

    @ApiModelProperty("支付状态 1代支付 2已支付")
    private String status;

    @ApiModelProperty("电话")
    private String mobile;
}
