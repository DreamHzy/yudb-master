package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MerchantSelectBoxVO implements Serializable {
    @ApiModelProperty("代理商id")
    private String id;

    @ApiModelProperty("代理商名称")
    private String Name;

}
