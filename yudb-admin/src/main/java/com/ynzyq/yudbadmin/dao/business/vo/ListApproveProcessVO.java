package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/24 15:29
 * @description:
 */
@Data
public class ListApproveProcessVO implements Serializable {
    @ApiModelProperty("审批流id")
    private String id;

    @ApiModelProperty("类型：1：门店，2：代理")
    private String type;

    @ApiModelProperty("审核类型：1、金额调整 2、延期支付 3、新订单审核 4、取消费用，5：线下支付")
    private String examineType;
}
