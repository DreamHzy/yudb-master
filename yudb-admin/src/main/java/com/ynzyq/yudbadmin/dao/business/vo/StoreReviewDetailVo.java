package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class StoreReviewDetailVo {

    private String id;

    @ApiModelProperty(value = "授权码")
    private String uid;


    @ApiModelProperty(value = "备注")
    private String remark;


    @ApiModelProperty(value = "管理费")
    private String managementExpense;

    @ApiModelProperty(value = "原合同到期时间")
    private String oldTime;

    @ApiModelProperty(value = "新合同到期时间")
    private String newTime;


    @ApiModelProperty(value = "审核状态")
    List<ExamineDetailVo> examineDetailVoList;


    @ApiModelProperty(value = "审批单")
    List<ExamineFileTwoVo> examineFileVoList;

}
