package com.yunzyq.yudbapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WJ
 */
@Getter
@AllArgsConstructor
public enum AuditFlowEnum {
    FIRST_INSTANCE(1, "一审"),
    SECOND_INSTANCE(2, "二审");
    /**
     * 审核流程
     */
    private Integer flow;

    /**
     * 文本说明
     */
    private String desc;
}