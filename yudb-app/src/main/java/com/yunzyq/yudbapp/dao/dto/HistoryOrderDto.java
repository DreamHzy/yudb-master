package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HistoryOrderDto {

    @ApiModelProperty("加盟商管理列表id")
    private String id;

    @ApiModelProperty("前段不需要管")
    private String regionalManagerId;
}
