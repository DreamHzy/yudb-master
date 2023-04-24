package com.yunzyq.yudbapp.enums;

import lombok.Getter;

/**
 * @author WJ
 */

@Getter
public enum OrderMasterTypeEnum {
    SYSTEM_GENERATION(1, "系统生成"),
    REGIONAL_MANAGER_GENERATION(2, "区域经理生成"),
    SYSTEM_BACKGROUND_PERSONNEL_GENERATION(3, "系统后台人员生成"),
    ;
    /**
     * 订单生成类型
     */
    private Integer type;

    /**
     * 文本说明
     */
    private String desc;

    OrderMasterTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
