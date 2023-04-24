package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StroeIdDto {
    @ApiModelProperty("门店标识")
    private String storeId;

    @ApiModelProperty("前端不需要管")
    private String merchantId;
}
