package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HistoryOrderVo {


    @ApiModelProperty("加盟商")
    private String merchantName;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("金额")
    private String money;

    @ApiModelProperty("支付方式")
    private String payTypeName;

    @ApiModelProperty("金额生成时间")
    private String createTime;
}
