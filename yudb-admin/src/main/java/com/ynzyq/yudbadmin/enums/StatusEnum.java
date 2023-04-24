package com.ynzyq.yudbadmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WJ
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
    ENABLE(1, "启用"),
    DISABLE(2, "停用");
    /**
     * 状态
     */
    private Integer status;

    /**
     * 文本说明
     */
    private String desc;
}
