package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MerchantMyVo {

    @ApiModelProperty("加盟商")
    private String name;

    @ApiModelProperty("加盟时间")
    private String time;


    @ApiModelProperty("我的代办数")
    private String count;

    @ApiModelProperty("代办区内容")
    private IndexOrderVo indexOrderVo;
}
