package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeFileVo {

    @ApiModelProperty("文件名称")
    private String name;
    @ApiModelProperty("下载地址")
    private String url;
}
