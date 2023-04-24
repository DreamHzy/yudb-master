package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class NoticeSeeVo {

    private String id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "通知类型 1普通 2精选")
    private String type;
    @ApiModelProperty(value = "用户群体 1全部 2加盟商 3区域经理")
    private String userType;
    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "是否置顶 1置顶 2不置顶")
    private String isTop;

    @ApiModelProperty(value = "附件内容")
    private List<NoticeFileVo> noticeFileVoList;

    @ApiModelProperty(value = "域名")
    private String imageurl;
}
