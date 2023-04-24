package com.ynzyq.yudbadmin.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpContextUtils {
    public static HttpServletRequest getHttpServletRequest() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
