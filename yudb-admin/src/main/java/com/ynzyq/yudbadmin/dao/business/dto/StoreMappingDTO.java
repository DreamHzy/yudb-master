package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/18 10:36
 * @description:
 */
@Data
public class StoreMappingDTO implements Serializable {
    @ApiModelProperty("省/市/区/区域经理")
    private String condition;

    public void setCondition(String condition) {
        if (StringUtils.isNotBlank(condition)) {
            this.condition = condition;
        }
    }
}