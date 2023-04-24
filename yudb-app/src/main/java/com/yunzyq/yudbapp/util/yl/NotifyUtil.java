package com.yunzyq.yudbapp.util.yl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.yunzyq.yudbapp.util.SecurityUtil.byte2Hex;

/**
 * @program: openPlatform
 * @description: 接收并处理支付结果通知的demo
 * @author: Mr.YS
 * @CreatDate: 2019/12/25/025 16:42
 */
@Slf4j
public class NotifyUtil {

    public static String makeSign(String md5Key, Map<String, String> params) {
        String preStr = buildSignString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String text = preStr + md5Key;
        System.out.println("待签名字符串：" + text);
        return String2SHA256StrJava(getContentBytes(text)).toUpperCase();
    }

    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param str 加密后的报文
     * @return
     */
    public static String String2SHA256StrJava(byte[] str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str);
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    // 构建签名字符串
    public static String buildSignString(Map<String, String> params) {

        // params.put("Zm","test_test");

        if (params == null || params.size() == 0) {
            return "";
        }

        List<String> keys = new ArrayList<>(params.size());

        for (String key : params.keySet()) {
            if ("sign".equals(key))
                continue;
            if (StringUtils.isEmpty(params.get(key)))
                continue;
            keys.add(key);
        }
        //System.out.println(listToString(keys));

        Collections.sort(keys);

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                buf.append(key + "=" + value);
            } else {
                buf.append(key + "=" + value + "&");
            }
        }

        return buf.toString();
    }

    // 根据编码类型获得签名内容byte[]
    public static byte[] getContentBytes(String content) {
        try {
            return content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误");
        }
    }
}
