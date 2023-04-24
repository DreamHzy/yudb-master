package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/14 17:36
 * @description:
 */
@Data
public class ListOverdueOrderDTO implements Serializable {
    @ApiModelProperty("区域/区域经理")
    private String condition;

    @ApiModelProperty("订单类型")
    private String type;

    @ApiModelProperty("开始日期")
    private String startTime;

    @ApiModelProperty("结束日期")
    private String endTime;

    @ApiModelProperty("缴费类型id")
    private String typeId;

    private String today;

    public void setType(String type) {
        if (StringUtils.isNotBlank(type)) {
            this.type = type;
        }
    }

    public void setStartTime(String startTime) {
        if (StringUtils.isNotBlank(startTime)) {
            this.startTime = startTime;
        }
    }

    public void setEndTime(String endTime) {
        if (StringUtils.isNotBlank(endTime)) {
            this.endTime = endTime;
        }
    }

    public void setCondition(String condition) {
        if (StringUtils.isNotBlank(condition)) {
            this.condition = condition;
        }
    }

    public void setTypeId(String typeId) {
        if (StringUtils.isNotBlank(typeId)) {
            this.typeId = typeId;
        }
    }
}
