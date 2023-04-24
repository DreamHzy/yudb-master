package com.yunzyq.yudbapp.enums;

import lombok.Getter;

/**
 * @author WJ
 */

@Getter
public enum IsDeleteEnum {
    NOT_DELETE(0, "未删除"),
    DELETE(1, "已删除");
    /**
     * 是否删除
     */
    private int isDelete;

    /**
     * 文本说明
     */
    private String desc;

    IsDeleteEnum(int isDelete, String desc) {
        this.isDelete = isDelete;
        this.desc = desc;
    }
}
