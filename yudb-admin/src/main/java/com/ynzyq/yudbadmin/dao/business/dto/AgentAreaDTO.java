package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/11/3
 */
@Data
public class AgentAreaDTO implements Serializable {
    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区县")
    private String area;

    @ApiModelProperty("代理权代码")
    private String uid;
}
