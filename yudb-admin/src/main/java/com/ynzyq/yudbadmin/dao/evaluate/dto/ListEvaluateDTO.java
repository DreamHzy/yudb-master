package com.ynzyq.yudbadmin.dao.evaluate.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/1 16:38
 * @description:
 */
@Data
public class ListEvaluateDTO implements Serializable {
    @ApiModelProperty("申请者/手机号")
    private String condition;

    public void setCondition(String condition) {
        if (StringUtils.isNotBlank(condition)) {
            this.condition = condition;
        }
    }
}
