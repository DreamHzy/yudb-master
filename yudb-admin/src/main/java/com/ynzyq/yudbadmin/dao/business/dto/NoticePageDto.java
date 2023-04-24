package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticePageDto {

    @ApiModelProperty("搜索通知标题")
    private String title;
}
