package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PayReviewDto {

    private String id;

    @ApiModelProperty("内容")
    private String msg;

    @ApiModelProperty("2通过 3拒绝")
    private String status;

    @ApiModelProperty("照片地址")
    private List<String> images;

}
