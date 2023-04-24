package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BannerStatus {

    private String id;

    @ApiModelProperty("1发布 2下架")
    private String status;
}
