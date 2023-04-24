package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApplyForAdjustmentDto {

    @ApiModelProperty("订单id标识")
    private String id;
    @ApiModelProperty("缴费类型标识")
    private String payTypeId;
    @ApiModelProperty("金额")
    private String money;
    @ApiModelProperty("时间 以天为单位")
    private String time;
    @ApiModelProperty("图片路径")
    private List<String> url;
    @ApiModelProperty("备注")
    private String msg;
}
