package com.ynzyq.yudbadmin.third.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/2 17:33
 * @description:
 */
@Data
public class ModifyStoreStatusDTO implements Serializable {
    /**
     * 授权号
     */
    private String uid;

    /**
     * 时间戳毫秒数
     */
    private String timeStamp;

    /**
     * 签名
     */
    private String sign;
}
