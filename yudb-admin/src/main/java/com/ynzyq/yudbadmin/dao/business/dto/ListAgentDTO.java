package com.ynzyq.yudbadmin.dao.business.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WJ
 */
@Data
public class ListAgentDTO implements Serializable {

    @ApiModelProperty(value = "加盟商/手机号")
    private String condition;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间 ")
    private String endTime;

    @ApiModelProperty(value = "代理商id")
    private String merchantId;

    @ApiModelProperty(value = "区域经理id")
    private String areaId;

}
