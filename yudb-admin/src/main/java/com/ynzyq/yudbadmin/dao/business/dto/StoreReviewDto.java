package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StoreReviewDto {

    private String id;

    @ApiModelProperty("内容")
    private String msg;

    @ApiModelProperty("2通过 3拒绝")
    private String status;
}
