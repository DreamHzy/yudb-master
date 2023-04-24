package com.ynzyq.yudbadmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WJ
 */
@Getter
@AllArgsConstructor
public enum StoreTypeEnum {
    STREET_SHOP("1", "街边店"),
    DEFERRED_PAYMENT("2", "商场店"),
    NEW_ORDER_REVIEW("3", "档口店"),
    ;
    /**
     * 门店类型
     */
    private String type;

    /**
     * 文本说明
     */
    private String desc;

    public static String getName(String type) {
        for (StoreTypeEnum storeTypeEnum : StoreTypeEnum.values()) {
            if (StringUtils.equals(storeTypeEnum.getType(), type)) {
                return storeTypeEnum.getDesc();
            }
        }
        return "";
    }
}
