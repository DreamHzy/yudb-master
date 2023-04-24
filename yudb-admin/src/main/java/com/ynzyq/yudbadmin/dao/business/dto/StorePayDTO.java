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
public class StorePayDTO implements Serializable {

    /**
     * 已收账单数量
     */
    private Integer actualReceivableCount;

    /**
     * 已收账单金额
     */
    private BigDecimal actualReceivableMoney;
}
