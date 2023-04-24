package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MerchantStoreOrderVo {
    @ApiModelProperty("费用类型")
    private String payTypeName;

    @ApiModelProperty("费用周期")
    private String cycle;

    @ApiModelProperty("缴费标准")
    private String money;

    @ApiModelProperty("应缴费金额")
    private String needMoney;

    @ApiModelProperty("已缴费金额")
    private String alerdMoney;

    @ApiModelProperty("缴费时间")
    private String payTime;

    @ApiModelProperty("是否调整")
    private String adjustment;

    @ApiModelProperty("调整内容")
    private String adjustmentMsg;
}
