package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/2/10 17:55
 * @description:
 */
@Data
public class ListFinanceOrderDTO extends FinancePageDto implements Serializable {
    @ApiModelProperty("type：1、未推送 2、已推送")
    private String type;

    public void setType(String type) {
        if (StringUtils.isNotBlank(type)) {
            this.type = type;
        }
    }
}
