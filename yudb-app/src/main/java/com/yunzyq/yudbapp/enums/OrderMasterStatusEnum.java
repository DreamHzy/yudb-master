package com.yunzyq.yudbapp.enums;


import lombok.Getter;

/**
 * @author WJ
 */

@Getter
public enum OrderMasterStatusEnum {
    TO_BE_PAID(1, "待支付"),
    PAYMENT_SUCCESSFUL(2, "支付成功"),
    CANCEL(3, "取消"),
    REVIEW_REJECTED(4, "审核拒绝");
    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 文本说明
     */
    private String desc;

    OrderMasterStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
