package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/24 15:54
 * @description:
 */
@Data
public class UpdateApproveProcessDTO extends AddApproveProcessDTO implements Serializable {
    @ApiModelProperty("审批流id")
    private String id;
}
