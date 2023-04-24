package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateRegionalManageDto {

    @ApiModelProperty("区域经理id")
    private String regionalManageId;

    @ApiModelProperty("门店id")
    private List<String> storeIds;
}
