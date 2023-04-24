package com.yunzyq.yudbapp.enums;

import lombok.Getter;

@Getter
public enum ExamineFileTypeEnum {
    EXCEL(1, "excel表格"),
    WORD(2, "word"),
    VIDEO(3, "视频"),
    PHOTO(4, "照片"),
    ;
    /**
     * 文件类型
     */
    private Integer type;

    /**
     * 文本说明
     */
    private String desc;

    ExamineFileTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
