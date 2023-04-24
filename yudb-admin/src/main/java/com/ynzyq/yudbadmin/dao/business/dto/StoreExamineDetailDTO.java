package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/27 11:10
 * @description:
 */
@Data
public class StoreExamineDetailDTO implements Serializable {
    @ApiModelProperty("审批单id")
    private String id;
}
