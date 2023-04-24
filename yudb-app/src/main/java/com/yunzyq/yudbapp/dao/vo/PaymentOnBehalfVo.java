package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaymentOnBehalfVo {

    @ApiModelProperty("支付二维码")
    private String qrCodeBase64;
}
