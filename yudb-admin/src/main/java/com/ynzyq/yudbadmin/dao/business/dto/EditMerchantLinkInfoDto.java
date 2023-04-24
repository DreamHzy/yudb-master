package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EditMerchantLinkInfoDto {

    @ApiModelProperty("门店标识")
    private String id;

    @ApiModelProperty("商户通 1开通 2未开通")
    private String status;

    @ApiModelProperty("商户通开通时间")
    private String merchantLinkTime;

    @ApiModelProperty("商户通到期时间")
    private String merchantLinkEndTime;
}
