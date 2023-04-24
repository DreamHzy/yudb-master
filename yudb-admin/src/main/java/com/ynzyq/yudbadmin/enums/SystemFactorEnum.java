package com.ynzyq.yudbadmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xinchen
 * @date 2022/4/27 10:49
 * @description:
 */
@Getter
@AllArgsConstructor
public enum SystemFactorEnum {
    ONE(1, "1.0"),
    TWO(2, "2.0"),
    THREE(3, "3.0"),
    ;
    /**
     * 代理体系
     */
    private Integer systemFactor;
    /**
     * 文本说明
     */
    private String desc;

    public static String getStatusDesc(Integer systemFactor) {
        for (SystemFactorEnum systemFactorEnum : SystemFactorEnum.values()) {
            if (systemFactorEnum.getSystemFactor().equals(systemFactor)) {
                return systemFactorEnum.getDesc();
            }
        }
        return "";
    }
}