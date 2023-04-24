package com.yunzyq.yudbapp.enums;

import lombok.Getter;

/**
 * @author WJ
 */

@Getter
public enum IsSendEnum {
    PUSHED(1, "已推送"),
    NOT_PUSHED(2, "未推送");
    /**
     * 订单是否已推送给加盟商
     */
    private Integer isSend;

    /**
     * 文本说明
     */
    private String desc;

    IsSendEnum(int isSend, String desc) {
        this.isSend = isSend;
        this.desc = desc;
    }
}
