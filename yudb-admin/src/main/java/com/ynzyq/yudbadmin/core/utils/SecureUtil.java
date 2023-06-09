package com.ynzyq.yudbadmin.core.utils;

import org.springframework.util.DigestUtils;

/**
 * 安全处理工具类
 * @author Caesar Liu
 * @date 2021-03-31 19:30
 */
public class SecureUtil {

    /**
     * 加密密码
     * @author Caesar Liu
     * @date 2021-03-31 19:30
     */
    public static String encryptPassword(String password, String salt) {
        return DigestUtils.md5DigestAsHex((password + salt).getBytes());
    }
}
