package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OneLevelStatusVO implements Serializable {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("说明")
    private String name;
}
