package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StoreReviewPageDto {
    @ApiModelProperty("申请人/授权号")
    private String condition;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间 ")
    private String endTime;
}
