package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepartmentUpdateDto {

    @ApiModelProperty(value = "部门名称")
    private String name;

    private String id;
}
