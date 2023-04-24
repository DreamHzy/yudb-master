package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class DepartmentAdd {

    @ApiModelProperty("上级部门（如果是根部门则传null）")
    private Integer parentId;

    @ApiModelProperty(value = "部门名称")
    private String name;
}
