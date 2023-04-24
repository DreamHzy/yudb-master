package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2021/12/27 11:31
 * @description:
 */
@Data
public class ApproveStepDetail implements Serializable {

    private String id;

    @ApiModelProperty("步骤，数字几就是代表第几步")
    private String step;

    @ApiModelProperty("步骤名称")
    private String name;

    @ApiModelProperty("类型：1：审批，2：会签")
    private String type;

    @ApiModelProperty("审批人/会签人id")
    private String userId;

    @ApiModelProperty("审批人/会签人姓名")
    private String userName;

    @ApiModelProperty("审核状态 1、待审核 2、通过 3、拒绝")
    private String status;

    @ApiModelProperty("审批意见")
    private String remark;

    @ApiModelProperty("审批时间")
    private String examineTime;
//
//    @ApiModelProperty("视频")
//    private List<ExamineFileDetail> videos;

    @ApiModelProperty("图片")
    private List<ExamineFileDetail> photos;
}
