package com.ynzyq.yudbadmin.dao.business.vo;

import com.ynzyq.yudbadmin.core.model.PageData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AgentFinanceListVo
{

    @ApiModelProperty("待缴费单数")
    private Integer needCount;

    @ApiModelProperty("待缴费金额")
    private BigDecimal needMoney;

    @ApiModelProperty("已缴费单数")
    private Integer alerdCount;

    @ApiModelProperty("已缴费金额")
    private BigDecimal alerdMoney;

    @ApiModelProperty
    PageData<AgentFinancePageVo> financePageVoList;
}
