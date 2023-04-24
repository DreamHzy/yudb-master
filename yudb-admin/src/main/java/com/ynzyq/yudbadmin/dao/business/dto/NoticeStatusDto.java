package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeStatusDto {

    private String id;

    @ApiModelProperty("状态 1发布 2未发布")
    private String status;
}
