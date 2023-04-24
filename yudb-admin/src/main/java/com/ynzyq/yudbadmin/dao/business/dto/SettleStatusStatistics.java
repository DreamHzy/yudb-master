package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/17 14:27
 * @description:
 */
@Data
public class SettleStatusStatistics implements Serializable {
    @ApiModelProperty("总数量")
    private Integer count;

    @ApiModelProperty("已结算数量")
    private Integer settleCount;

    @ApiModelProperty("未结算数量")
    private Integer notSettleCount;
}
