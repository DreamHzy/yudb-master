package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExamineDetailVo {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "状态")
    private String examineStatus;

    @ApiModelProperty(value = "审批人")
    private String examineName;

    @ApiModelProperty(value = "审批意见")
    private String examineMsg;

    @ApiModelProperty(value = "审批单照片")
    private List<String> images;

    private String url;
}
