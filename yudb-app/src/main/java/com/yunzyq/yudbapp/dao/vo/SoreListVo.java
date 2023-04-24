package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SoreListVo {

    @ApiModelProperty("门店地址")
    private String address;

    @ApiModelProperty("门店标识")
    private String id;
}
