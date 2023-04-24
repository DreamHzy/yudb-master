package com.ynzyq.yudbadmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WJ
 */
@Getter
@AllArgsConstructor
public enum StatisticsSettleStatusEnum {
    SETTLED("1", "已结算"),
    SETTLEMENT_FAILED("2", "未结算"),
    SETTLEMENT_EXCEPTION("3", "结算异常"),
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
        for (StatisticsSettleStatusEnum statisticsSettleStatusEnum : StatisticsSettleStatusEnum.values()) {
            if (StringUtils.equals(statisticsSettleStatusEnum.getStatus(), status)) {
                return statisticsSettleStatusEnum.getDesc();
            }
        }
        return "";
    }
}
