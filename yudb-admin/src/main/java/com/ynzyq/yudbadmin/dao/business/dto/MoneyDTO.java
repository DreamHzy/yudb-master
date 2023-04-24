package com.ynzyq.yudbadmin.dao.business.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xinchen
 * @date 2021/11/23 9:58
 * @description:
 */
@Data
public class MoneyDTO implements Serializable {
    private Integer count;
    private BigDecimal money;
}
