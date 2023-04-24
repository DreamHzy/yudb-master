package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CloudSchool {

    private String id;

    @ApiModelProperty("开始时间")
    private String time;

    @ApiModelProperty("到期时间")
    private String endTime;

    @ApiModelProperty("账号")
    private String account;
}
