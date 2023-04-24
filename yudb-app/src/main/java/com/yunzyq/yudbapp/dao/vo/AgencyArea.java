package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Data
public class AgencyArea implements Serializable {
    @ApiModelProperty("代理权代码")
    private String uid;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区域")
    private String area;
}
