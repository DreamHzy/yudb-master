package com.yunzyq.yudbapp.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    DATA_EMPTY(200, "请求成功"),
    DATA_EXISTS(-200, "请求失败"),
    PWD_INCORRECT(-100, "未登陆"),
    ;

    private Integer code;

    private String message;
}
