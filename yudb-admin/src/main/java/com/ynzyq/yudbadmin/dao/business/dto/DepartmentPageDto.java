package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DepartmentPageDto {

    @ApiModelProperty(value = "关键字搜索")
    private String condition;


}
