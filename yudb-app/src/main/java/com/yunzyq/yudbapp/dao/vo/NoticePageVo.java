package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticePageVo {

    private String id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容(后台会全部展示，前端显示部分即可)")
    private String msg;

}
