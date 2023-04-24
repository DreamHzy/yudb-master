package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeTypeDto {


    private String id;

    @ApiModelProperty("通知类型 1普通 2精选")
    private String type;
}
