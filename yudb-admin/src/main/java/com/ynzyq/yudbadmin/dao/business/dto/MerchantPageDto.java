package com.ynzyq.yudbadmin.dao.business.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MerchantPageDto {

    @ApiModelProperty(value = "关键字搜索")
    private String condition;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间 ")
    private String endTime;


}
