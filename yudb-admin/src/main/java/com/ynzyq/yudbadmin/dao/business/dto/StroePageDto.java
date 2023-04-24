package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StroePageDto {

    @ApiModelProperty(value = "关键字搜索")
    private String condition;

    @ApiModelProperty(value = "签约开始时间")
    private String startSignTime;

    @ApiModelProperty(value = "签约结束时间 ")
    private String endSignTime;

    @ApiModelProperty(value = "合同到期开始时间")
    private String startExpireTime;

    @ApiModelProperty(value = "合同到期结束时间 ")
    private String endExpireTime;

    @ApiModelProperty(value = "代理商id")
    private String merchantId;

    @ApiModelProperty(value = "区域经理id")
    private String areaId;

    @ApiModelProperty("收款月份")
    private String month;

    @ApiModelProperty("一级状态")
    private String status;

    @ApiModelProperty("延期开业")
    private String delayedOpen;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("区域")
    private String region;

    @ApiModelProperty("服务开始时间")
    private String startServiceExpireTime;

    @ApiModelProperty("服务到期时间")
    private String endServiceExpireTime;

    @ApiModelProperty("是否申请调货：1：开，2：关")
    private String isApply;
}
