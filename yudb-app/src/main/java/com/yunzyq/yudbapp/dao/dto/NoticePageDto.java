package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticePageDto {
    @ApiModelProperty("2加盟商 3区域经理")
    private String type;
    @ApiModelProperty("通知类型 1普通 2精选")
    private String noticeType;
}
