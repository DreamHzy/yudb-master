package com.ynzyq.yudbadmin.dao.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/18 22:30
 * @description:
 */
@Data
public class PaymentOrderDTO implements Serializable {
    /**
     * 订单id
     */
    private Integer id;

    /**
     * 订单状态
     */
    private Integer status;
}
