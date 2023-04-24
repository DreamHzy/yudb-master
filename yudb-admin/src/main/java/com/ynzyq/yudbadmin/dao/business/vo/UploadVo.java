package com.ynzyq.yudbadmin.dao.business.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UploadVo {
    @ApiModelProperty("上传路径")
    private String path;

    @ApiModelProperty("文件地址")
    private String imageurl;

    @ApiModelProperty("文件名称")
    private String fileName;
}
