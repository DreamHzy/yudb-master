package com.yunzyq.yudbapp.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述：
 * <p>
 * &nbsp;&nbsp;&nbsp;&nbsp;ip转换byte数组管理类。
 * </p>
 * 创建日期：2012-7-25 下午3:34:00<br>
 *
 * @author：Wendy<br>
 * @update：$Date: 2012-07-25 18:14:40 +0800 (Wed, 25 Jul 2012) $<br>
 * @version：$Revision: 779 $<br>
 * @since 1.0.0
 */
public class IpUtil {

    /**
     * 获取登录用户的IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

}
