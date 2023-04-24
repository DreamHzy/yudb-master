package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class NoticeAddDto {
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("通知类型 1普通 2精选")
    private String type;
    @ApiModelProperty("用户群体 1全部 2加盟商 3区域经理")
    private String userType;
    @ApiModelProperty("是否置顶 1置顶 2不置顶")
    private String isTop;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("附件内容")
    private List<NoticeFileDto> noticeFileDtoList;
}
