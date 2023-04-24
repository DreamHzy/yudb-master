package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/24 16:10
 * @description:
 */
@Data
public class ApproveProcessStepDTO implements Serializable {
    @ApiModelProperty("审批流步骤id")
    private String id;

    @ApiModelProperty("步骤")
    private String step;

    @ApiModelProperty("步骤名称")
    private String name;

    @ApiModelProperty("类型：1：审批，2：会签")
    private String type;

    @ApiModelProperty("审批人/会签人id")
    private String userId;

    @ApiModelProperty("审批人/会签人姓名")
    private String userName;
}
