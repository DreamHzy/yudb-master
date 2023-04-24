package com.ynzyq.yudbadmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WJ
 */
@Getter
@AllArgsConstructor
public enum AdjustmentTypeEnum {
    AMOUNT_ADJUSTMENT("1", "金额调整"),
    DEFERRED_PAYMENT("2", "延期支付"),
    NEW_ORDER_REVIEW("3", "新订单审核"),
    CANCEL_PAYMENT("4", "取消缴费"),
    OFFLINE_PAYMENTS("5", "线下支付"),
    ;
    /**
     * 申请调整类型
     */
    private String type;

    /**
     * 文本说明
     */
    private String desc;

    public static String getName(String type) {
        for (AdjustmentTypeEnum adjustmentTypeEnum : AdjustmentTypeEnum.values()) {
            if (StringUtils.equals(adjustmentTypeEnum.getType(), type)) {
                return adjustmentTypeEnum.getDesc();
            }
        }
        return "";
    }
}
