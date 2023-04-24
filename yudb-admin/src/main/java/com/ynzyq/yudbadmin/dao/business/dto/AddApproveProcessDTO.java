package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2021/12/24 15:54
 * @description:
 */
@Data
public class AddApproveProcessDTO implements Serializable {
    @ApiModelProperty("类型：1：门店，2：代理")
    private String type;

    @ApiModelProperty("审核类型：1、金额调整 2、延期支付 3、新订单审核 4、取消费用")
    private String examineType;

    @ApiModelProperty("审批流步骤")
    private List<AddApproveProcessStepDTO> approveProcessSteps;
}
