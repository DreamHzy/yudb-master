package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthQueryVo {

    @ApiModelProperty("是否需要授权 1需要 2不需要")
    private String isSend;

    @ApiModelProperty("模板id")
    private String id;
}
