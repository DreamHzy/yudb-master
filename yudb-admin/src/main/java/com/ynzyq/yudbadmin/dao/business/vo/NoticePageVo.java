package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticePageVo {

    private String id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("通知类型 1普通 2精选")
    private String type;
    @ApiModelProperty("用户群体 1全部 2加盟商 3区域经理")
    private String userType;
    @ApiModelProperty("状态 1发布 2未发布")
    private String status;
    @ApiModelProperty("是否置顶 1置顶 2不置顶")
    private String isTop;
    @ApiModelProperty("发布人")
    private String createUser;
    @ApiModelProperty("创建时间")
    private String createTime;


}
