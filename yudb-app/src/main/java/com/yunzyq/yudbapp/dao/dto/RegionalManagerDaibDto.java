package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegionalManagerDaibDto {


    @ApiModelProperty(value = "1当前代办 2历史代办")
    private String type;

    @ApiModelProperty(value = "前段不需要管")
    private String id;
}
