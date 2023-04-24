package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class NoticeDetailVo {
    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("发布时间")
    private String time;


    @ApiModelProperty("内容(后台会全部展示，前端显示部分即可)")
    private String msg;

    @ApiModelProperty("文件列表")
    private List<NoticeFileVo> list;


}
