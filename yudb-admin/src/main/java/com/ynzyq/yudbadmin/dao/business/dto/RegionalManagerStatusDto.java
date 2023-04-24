package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegionalManagerStatusDto {

    private String id;

    @ApiModelProperty("1启用2停用")
    private String status;
}
