package com.ynzyq.yudbadmin.exception;

import lombok.Getter;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/20
 */
@Getter
public class BaseException extends RuntimeException implements Serializable {
    /**
     * 异常标识码
     */
    private int code;
    /**
     * 异常说明
     */
    private String msg;

    public BaseException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
