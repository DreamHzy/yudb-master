package com.yunzyq.yudbapp.dao.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FileDto {

    @ApiModelProperty("文件地址")
    private String url;

    @ApiModelProperty("文件名称")
    private String name;

}
