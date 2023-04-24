package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2021/12/24 16:10
 * @description:
 */
@Data
public class AddApproveProcessStepDTO implements Serializable {
    @ApiModelProperty("步骤")
    private String step;

    @ApiModelProperty("步骤名称")
    private String name;

    @ApiModelProperty("审批人数组")
    private List<String> approveUserIds;

    @ApiModelProperty("会签人数组")
    private List<String> signUserIds;
}
