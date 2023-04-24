package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PopupVo {

    @ApiModelProperty(value = "审核类型")
    private String examineType;

    @ApiModelProperty(value = "具体内容")
    private String reviewMsg;


    @ApiModelProperty(value = "一审审批人")
    private String examineOneName;

    @ApiModelProperty(value = "一审审批意见")
    private String examineOneMsg;

    @ApiModelProperty(value = "审批单照片")
    private List<String> images;

    private String url;
}
