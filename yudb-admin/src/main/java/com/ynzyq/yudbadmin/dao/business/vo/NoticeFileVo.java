package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeFileVo {

    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "文件下载地址")
    private String url;

    private String imageurl;
}
