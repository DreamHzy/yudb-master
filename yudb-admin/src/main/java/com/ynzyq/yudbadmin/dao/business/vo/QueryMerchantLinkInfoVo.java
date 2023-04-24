package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class QueryMerchantLinkInfoVo {

    @ApiModelProperty("商户通 1开通 2未开通")
    private String status;

    @ApiModelProperty("商户通开通时间")
    private Date merchantLinkTime;

    @ApiModelProperty("商户通到期时间")
    private Date merchantLinkEndTime;

}
