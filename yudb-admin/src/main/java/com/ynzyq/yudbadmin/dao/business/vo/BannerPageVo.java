package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BannerPageVo {

    private String id;

    @ApiModelProperty("图片地址")
    private String icon;
    @ApiModelProperty("图片地址")
    private String url;
    @ApiModelProperty("图片地址")
    private String sort;
    @ApiModelProperty("创建者")
    private String realName;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("1发布 2下架")
    private String status;
}
