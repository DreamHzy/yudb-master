package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/27 13:10
 * @description:
 */
@Data
public class ExamineTypeVO implements Serializable {
    @ApiModelProperty("审核类型id")
    private String examineType;

    @ApiModelProperty("审核类型")
    private String examineTypeName;
}
