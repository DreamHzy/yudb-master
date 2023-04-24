package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BannerAddDto {

    @ApiModelProperty("图片地址(上传时返回的想对路径)")
    private String url;

    @ApiModelProperty("1轮播一  2轮播二  3轮播三")
    private String sort;
}
