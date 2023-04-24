package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateStatusTwoDTO {

    @ApiModelProperty("门店id")
    private String storeId;
    @ApiModelProperty("状态id")
    private String statusTwo;
}
