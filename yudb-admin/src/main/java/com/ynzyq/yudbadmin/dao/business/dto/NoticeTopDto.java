package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeTopDto {

    private String id;

    @ApiModelProperty("是否置顶 1置顶 2不置顶")
    private String isTop;
}
