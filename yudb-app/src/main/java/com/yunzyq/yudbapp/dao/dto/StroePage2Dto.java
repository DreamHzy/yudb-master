package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StroePage2Dto {
    @ApiModelProperty(value = "关键字搜索")
    private String condition;
    @ApiModelProperty(value = "前端不需要管")
    private String id;

    @ApiModelProperty("一级状态")
    @NotBlank(message = "一级状态不能为空")
    private String status;

}
