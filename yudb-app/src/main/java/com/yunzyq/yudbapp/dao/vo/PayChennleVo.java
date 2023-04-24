package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayChennleVo {

    @ApiModelProperty("订单id")
    private String id;

    private String code;

    @ApiModelProperty("加盟商")
    private String merchantName;

    @ApiModelProperty("授权码")
    private String uid;

    @ApiModelProperty("缴费金额")
    private String money;

    @ApiModelProperty("支付H5地址")
    private String url;

    @ApiModelProperty("支付二维码")
    private String qrCodeBase64;

    @ApiModelProperty("小程序支付信息")
    private String payInfo;

    private Integer status;

    private String msg;


}
