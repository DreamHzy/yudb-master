package com.ynzyq.yudbadmin.dao.business.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xinchen
 * @date 2021/12/8 17:17
 * @description:
 */
@Data
public class StoreBillDTO implements Serializable {
    /**
     * 应收账单数量
     */
    private Integer accountReceivableCount;

    /**
     * 应收账单金额
     */
    private BigDecimal accountReceivableMoney;

    /**
     * 已收账单数量
     */
    private Integer actualReceivableCount;

    /**
     * 已收账单金额
     */
    private BigDecimal actualReceivableMoney;


    /**
     * 已推送账单数量
     */
    private Integer sendCount;

    /**
     * 已推送账单金额
     */
    private BigDecimal sendMoney;

    /**
     * 未推送账单数量
     */
    private Integer notSendCount;

    /**
     * 未推送账单金额
     */
    private BigDecimal notSendMoney;
}
