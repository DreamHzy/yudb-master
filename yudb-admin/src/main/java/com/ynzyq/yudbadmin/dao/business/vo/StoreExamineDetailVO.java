package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2021/12/27 11:19
 * @description:
 */
@Data
public class StoreExamineDetailVO implements Serializable {
    @ApiModelProperty("审批单详情")
    private StoreExamineDetail storeExamineDetail;

    @ApiModelProperty("审批流详情")
    private List<ApproveStepDetail> approveStepDetails;

    @ApiModelProperty("视频")
    private List<ExamineFileDetail> videos;

    @ApiModelProperty("图片")
    private List<ExamineFileDetail> photos;
}
