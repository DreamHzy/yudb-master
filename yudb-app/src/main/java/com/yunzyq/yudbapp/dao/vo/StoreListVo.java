package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StoreListVo {

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("省市区")
    private String provinceAndCityAndArea;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("签约时间")
    private String signTime;

    @ApiModelProperty("到期时间")
    private String expireTime;

    @ApiModelProperty("签约人")
    private String signName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("一级状态")
    private String status;


}
