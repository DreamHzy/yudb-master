package com.ynzyq.yudbadmin.third.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/15 20:43
 * @description:
 */
@Data
public class QuerySettleStatusVO implements Serializable {
    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("结算状态：1：已完成，2：待结算，3：结算中，4：结算失败，5：已关闭")
    private String settleStatus;
}
