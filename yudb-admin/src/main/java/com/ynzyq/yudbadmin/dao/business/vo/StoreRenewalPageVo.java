package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StoreRenewalPageVo {


    private String id;

    @ApiModelProperty(value = "申请人")
    private String applicant;

    @ApiModelProperty(value = "授权码")
    private String uid;


    @ApiModelProperty(value = "原合同管理费")
    private String oldMoney;

    @ApiModelProperty(value = "新合同管理费")
    private String newMoney;

    @ApiModelProperty(value = "一审状态")
    private String examineOneStatus;

    @ApiModelProperty(value = "二审状态")
    private String examineTwoStatus;


    @ApiModelProperty(value = "申请时间")
    private String createTime;
}
