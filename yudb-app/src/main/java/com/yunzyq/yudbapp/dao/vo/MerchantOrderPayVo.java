package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MerchantOrderPayVo {

    @ApiModelProperty("小程序支付信息")
    private String payInfo;


}
