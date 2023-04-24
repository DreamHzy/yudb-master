package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PayReviewDetailVo {


    private String id;

    @ApiModelProperty(value = "授权码")
    private String uid;

    @ApiModelProperty(value = "加盟商")
    private String merchantName;


    @ApiModelProperty(value = "费用类型")
    private String payTypeName;


    @ApiModelProperty(value = "金额")
    private String money;

    @ApiModelProperty(value = "申请人")
    private String applicant;

    @ApiModelProperty(value = "申请时间")
    private Date createTime;

    @ApiModelProperty(value = "缴费截止日期")
    private Date expireTime;

    @ApiModelProperty(value = "审核类型")
    private String examineType;


    @ApiModelProperty(value = "具体内容")
    private String reviewMsg;


    @ApiModelProperty(value = "审核状态")
    List<ExamineDetailVo> examineDetailVoList;


    @ApiModelProperty(value = "备注")
    private String remark;


    @ApiModelProperty(value = "审批单")
    List<ExamineFileVo> examineFileVoList;

}
