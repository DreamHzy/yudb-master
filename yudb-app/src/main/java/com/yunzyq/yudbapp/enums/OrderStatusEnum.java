package com.yunzyq.yudbapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xinchen
 * @date 2022/2/7 16:03
 * @description:
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    CREATE(0, "生成订单"),
    PENDING_PAY(1, "待付款"),
    IN_PROGRESS(2, "进行中"),
    IN_DELIVERY(3, "配送中"),
    COMPLETED(4, "已完成"),
    CANCELLED(10, "已取消"),
    ;
    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 订单状态文本说明
     */
    private String desc;

    public static String getStatusDesc(Integer status) {
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if (orderStatusEnum.getStatus().equals(status)) {
                return orderStatusEnum.getDesc();
            }
        }
        return "";
    }
}
