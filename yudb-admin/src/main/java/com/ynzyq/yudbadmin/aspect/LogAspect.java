package com.ynzyq.yudbadmin.aspect;

import com.ynzyq.yudbadmin.annotation.Log;
import com.ynzyq.yudbadmin.ansy.LogAnsy;
import com.ynzyq.yudbadmin.core.model.LoginUserInfo;
import com.ynzyq.yudbadmin.dao.system.model.SystemLog;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;
import com.ynzyq.yudbadmin.util.HttpContextUtils;
import com.ynzyq.yudbadmin.util.IpUtil;
import com.ynzyq.yudbadmin.util.JSONUtils;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;


/**
 * 日志系统切面
 */
@Aspect
@Component
public class LogAspect {


    @Resource
    LogAnsy logAnsy;


    @Pointcut("@annotation(com.ynzyq.yudbadmin.annotation.Log)")
    public void logPointCut() {

    }


    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @AfterReturning(value = "logPointCut()", returning = "keys")
    public void saveOperLog(JoinPoint joinPoint, Object keys) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Log syslog = method.getAnnotation(Log.class);
            SystemLog operlog = new SystemLog();
            if (syslog != null) {
                // 注解上的描述
                operlog.setDescription(syslog.value());
            }
            // 获取操作
            // 获取请求的类名
            String url = request.getRequestURI();
            operlog.setUrl(url);
            // 设置IP地址
            operlog.setIp(IpUtil.getIpAddr(request));
            operlog.setCreateTime(new Date());
            operlog.setUpdateTime(new Date());
            //获取当前用户信息
            LoginUserInfo user = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
            operlog.setCreateUser(user.getId());
            // 获取请求的参数
            Object[] args = joinPoint.getArgs();
            if (args.length > 0) {
                String params = JSONUtils.beanToJson(args[0]);
                operlog.setParameter(params);
            }
            operlog.setOperteResult(JSON.toJSONString(keys)); // 返回结果
            logAnsy.save(operlog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
