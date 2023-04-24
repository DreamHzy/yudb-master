package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegionalManagerPageDto {

    @ApiModelProperty("关键词")
    private String condition;
}
