package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/24 15:14
 * @description:
 */
@Data
public class ListApproveProcessDTO implements Serializable {
    @ApiModelProperty("对象类型：1：门店，2：代理")
    private String type;

    @ApiModelProperty("审核类型：1、金额调整 2、延期支付 3、新订单审核 4、取消费用，5：线下支付")
    private String examineType;

    public void setType(String type) {
        if (StringUtils.isNotBlank(type)) {
            this.type = type;
        }
    }

    public void setExamineType(String examineType) {
        if (StringUtils.isNotBlank(examineType)) {
            this.examineType = examineType;
        }
    }
}
