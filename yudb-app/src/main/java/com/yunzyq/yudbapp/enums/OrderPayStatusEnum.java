package com.yunzyq.yudbapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单支付状态枚举
 * @author WJ
 */
@Getter
@AllArgsConstructor
public enum OrderPayStatusEnum {
    ORDER_NOW(1, "下单中"),
    SUCCESSFULLY_ORDERED(2, "下单成功"),
    PAYMENT_SUCCESSFUL(3, "支付成功"),
    REVOKE(4, "撤销"),
    REFUND(5, "退款"),
    CLOSURE(6, "关闭"),
    ORDER_FAILED(7, "下单失败"),
    PAYMENT_FAILED(8, "支付失败"),
    ABNORMAL_PAYMENT_AMOUNT(9, "支付金额异常"),
    ;
    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 文本说明
     */
    private String desc;
}
