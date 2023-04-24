package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FileVo {

    @ApiModelProperty("文件地址")
    private String url;

    @ApiModelProperty("文件名称")
    private String name;
}
