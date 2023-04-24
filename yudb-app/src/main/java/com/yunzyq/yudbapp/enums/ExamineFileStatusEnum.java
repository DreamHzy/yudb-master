package com.yunzyq.yudbapp.enums;

import lombok.Getter;

@Getter
public enum ExamineFileStatusEnum {
    VALID(1, "有效"),
    INVALID(2, "无效");
    /**
     * 文件类型
     */
    private Integer status;

    /**
     * 文本说明
     */
    private String desc;

    ExamineFileStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
