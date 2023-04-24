package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MonitorUserStatusDto {

    private String id;

    @ApiModelProperty("1有效 2无效")
    private String status;
}
