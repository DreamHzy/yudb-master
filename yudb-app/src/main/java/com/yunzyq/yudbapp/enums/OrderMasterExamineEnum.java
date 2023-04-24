package com.yunzyq.yudbapp.enums;

import lombok.Getter;

/**
 * @author WJ
 */

@Getter
public enum OrderMasterExamineEnum {
    UNREVIEWED(1, "未审核"),
    IN_REVIEW(2, "审核中");
    /**
     * 订单是否在审核
     */
    private Integer status;

    /**
     * 文本说明
     */
    private String desc;

    OrderMasterExamineEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
