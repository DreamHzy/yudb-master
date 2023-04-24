package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegionalManageMyVo {

    @ApiModelProperty("区域经理")
    private String name;

    @ApiModelProperty("我的代办数")
    private String count;

    @ApiModelProperty("代办区内容")
    private IndexOrderVo indexOrderVo;
}
