package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/6/16 16:51
 * @description:
 */
@Data
public class SettleStatisticsDetailDTO implements Serializable {
    @ApiModelProperty("结算id")
    private String statisticsId;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("结算状态：1：已完成，2：待结算，3：结算中，4：结算失败，5：已关闭")
    private List<String> settleStatusList;

    public void setOrderNo(String orderNo) {
        if (StringUtils.isNotBlank(orderNo)) {
            this.orderNo = orderNo;
        }
    }
}