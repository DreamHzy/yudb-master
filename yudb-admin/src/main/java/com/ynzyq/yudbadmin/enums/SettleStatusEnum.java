package com.ynzyq.yudbadmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WJ
 */
@Getter
@AllArgsConstructor
public enum SettleStatusEnum {
    COMPLETED("1", "已完成"),
    PENDING_SETTLEMENT("2", "待结算"),
    SETTLEMENT("3", "结算中"),
    SETTLEMENT_FAILED("4", "结算失败"),
    CLOSED("5", "已关闭"),
    ;
    /**
     * 申请调整类型
     */
    private String status;

    /**
     * 文本说明
     */
    private String desc;

    public static String getName(String status) {
        for (SettleStatusEnum settleStatusEnum : SettleStatusEnum.values()) {
            if (StringUtils.equals(settleStatusEnum.getStatus(), status)) {
                return settleStatusEnum.getDesc();
            }
        }
        return "";
    }
}
