package com.yunzyq.yudbapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xinchen
 * @date 2022/2/18 14:16
 * @description:
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum {
    FIRST_MATCH(1, "首配"),
    SECOND_PAIR(2, "二配"),
//    OFFLINE_ORDER(3, "线下下单"),
    ;
    /**
     * 类型
     */
    private Integer type;

    /**
     * 类型文本说明
     */
    private String desc;
}
