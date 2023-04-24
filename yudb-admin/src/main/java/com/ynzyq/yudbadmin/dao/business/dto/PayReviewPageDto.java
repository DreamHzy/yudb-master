package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayReviewPageDto {

    @ApiModelProperty("审核类型标识")
    private String paymentTypeId;


    @ApiModelProperty("申请人/授权号")
    private String condition;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间 ")
    private String endTime;

    @ApiModelProperty(value = "一审状态")
    private String examineOneStatus;

    @ApiModelProperty(value = "二审状态")
    private String examineTwoStatus;

    @ApiModelProperty(value = "审核类型")
    private String examineType;

}
