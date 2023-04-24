package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddCloudSchoolDto {

    @ApiModelProperty("授权号")
    private String uid;
    @ApiModelProperty("开始时间")
    private String startTime;
    @ApiModelProperty("结束时间")
    private String endTime;
}
