package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexOrderVo {

    @ApiModelProperty("内容(管理费：20000.00元)")
    private String msg;

    private String money;

    private String name;

    private String id;
}
