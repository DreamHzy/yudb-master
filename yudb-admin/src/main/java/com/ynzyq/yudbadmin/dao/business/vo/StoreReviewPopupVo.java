package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StoreReviewPopupVo {

    @ApiModelProperty(value = "一审审批人")
    private String examineOneName;

    @ApiModelProperty(value = "一审审批意见")
    private String examineOneMsg;
}
