package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/14 16:54
 * @description:
 */
@Data
public class ManageStatementDTO implements Serializable {
    @ApiModelProperty("区域/区域经理")
    private String condition;

    @ApiModelProperty("缴费类型id")
    private String typeId;

    @ApiModelProperty("订单类型")
    private String type;

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

    public void setType(String type) {
        if (StringUtils.isNotBlank(type)) {
            this.type = type;
        }
    }
}
