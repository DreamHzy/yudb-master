package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubmitForReviewDto {

    @ApiModelProperty()
    private String id;

    @ApiModelProperty("缴费类型标识")
    private String payTypeId;

    @ApiModelProperty("缴费金额")
    private String money;

    @ApiModelProperty("费用说明")
    private String remark;

    @ApiModelProperty("缴费截至时间")
    private String time;

    @ApiModelProperty("照片")
    private List<String> photos;

    @ApiModelProperty("视频")
    private List<String> video;

    @ApiModelProperty("云学堂账号id数组")
    private List<String> accountIds;

}
