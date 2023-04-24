package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StroePageDto {
    @ApiModelProperty(value = "关键字搜索")
    private String condition;
    @ApiModelProperty(value = "前端不需要管")
    private String id;

}
