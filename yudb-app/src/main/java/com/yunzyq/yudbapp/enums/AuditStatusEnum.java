package com.yunzyq.yudbapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WJ
 */
@Getter
@AllArgsConstructor
public enum AuditStatusEnum {
    PENDING_REVIEW(1, "待审核"),
    PASS(2, "通过"),
    REFUSE(3, "拒绝"),
    ;
    /**
     * 审核状态
     */
    private Integer status;

    /**
     * 文本说明
     */
    private String desc;
}