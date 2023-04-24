package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BelongAgencyVO implements Serializable {

    @ApiModelProperty("代理权id")
    private Integer id;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("加盟商名称")
    private String merchantName;

}
