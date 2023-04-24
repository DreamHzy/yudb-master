package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeFileDto {

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("文件下载地址（相对路径）")
    private String url;
}
