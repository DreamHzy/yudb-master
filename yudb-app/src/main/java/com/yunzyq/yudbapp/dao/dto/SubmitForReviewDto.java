package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubmitForReviewDto {

    @ApiModelProperty("加盟商管理列表中的id")
    private String id;

    @ApiModelProperty("缴费类型标识")
    private String payTypeId;

    @ApiModelProperty("缴费金额")
    private String money;

    @ApiModelProperty("费用说明")
    private String remark;

    @ApiModelProperty("缴费截至时间")
    private String time;

    @ApiModelProperty("照片")
    private List<String> photos;


    @ApiModelProperty("视频")
    private List<String> video;

}
