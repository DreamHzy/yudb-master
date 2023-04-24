package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author wj
 * @Date 2021/11/3
 */
@Data
public class MyAgentAreaVO implements Serializable {
    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("合同开始时间")
    private String startTime;

    @ApiModelProperty("合同到期时间")
    private String expireTime;

    @ApiModelProperty("代理区域")
    private List<AgencyArea> areaList;
}
