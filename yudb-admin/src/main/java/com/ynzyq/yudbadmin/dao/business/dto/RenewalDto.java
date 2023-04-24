package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RenewalDto {

    @ApiModelProperty("门店标识")
    private String id;

    @ApiModelProperty(value = "管理费")
    private String managementExpense;

    @ApiModelProperty(value = "图片")
    private List<String> images;


    @ApiModelProperty(value = "文档")
    private List<String> words;
}
