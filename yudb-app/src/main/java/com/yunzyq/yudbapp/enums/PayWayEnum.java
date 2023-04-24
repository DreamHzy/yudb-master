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
public enum PayWayEnum {
    ALIPAY(1, "2", "1", "支付宝"),
    WECHAT(2, "1", "2", "微信"),
    BANK_PAY(3, "4", "", "银行卡"),
    QUICK_PAY(4, "3", "1", "云闪付"),
    UNION_PAY(5, "6", "3", "聚合码"),
    INTERNET_BANK(6, "7", "", "网银支付"),
    WHOLE_PEOPLE(7, "8", "", "全民付"),
    ;
    /**
     * 支付方式
     */
    private Integer way;

    /**
     * 对应支付通道的支付方式
     */
    private String type;

    /**
     * 对应支付通道的退款类型
     */
    private String refundType;

    /**
     * 文本说明
     */
    private String desc;

    public static String getPayWayDesc(Integer way) {
        for (PayWayEnum payWayEnum : PayWayEnum.values()) {
            if (payWayEnum.getWay().equals(way)) {
                return payWayEnum.getDesc();
            }
        }
        return "";
    }

    public static String getType(Integer way) {
        for (PayWayEnum payWayEnum : PayWayEnum.values()) {
            if (payWayEnum.getWay().equals(way)) {
                return payWayEnum.getType();
            }
        }
        return "";
    }

    public static String getRefundType(Integer way) {
        for (PayWayEnum payWayEnum : PayWayEnum.values()) {
            if (payWayEnum.getWay().equals(way)) {
                return payWayEnum.getRefundType();
            }
        }
        return "";
    }
}
