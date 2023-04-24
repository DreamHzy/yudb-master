package com.yunzyq.yudbapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WJ
 */
@Getter
@AllArgsConstructor
public enum AgentPayTypeEnum {
    MANAGEMENT_FEE("1", "管理费");
    /**
     * 缴费类型
     */
    private String type;

    /**
     * 文本说明
     */
    private String desc;
}