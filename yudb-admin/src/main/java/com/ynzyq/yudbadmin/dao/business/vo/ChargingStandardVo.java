package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChargingStandardVo {

    @ApiModelProperty("费用类型")
    private String payType;

    @ApiModelProperty("收费标准")
    private String money;

    @ApiModelProperty("账户是否开通")
    private String open;

    @ApiModelProperty("账户数量")
    private String num;
}
