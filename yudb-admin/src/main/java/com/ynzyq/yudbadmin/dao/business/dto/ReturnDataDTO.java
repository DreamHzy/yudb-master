package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReturnDataDTO implements Serializable {

    @ApiModelProperty("门店id")
    private Integer id;

}
