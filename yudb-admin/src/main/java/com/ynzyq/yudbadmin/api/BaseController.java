package com.ynzyq.yudbadmin.api;

import com.alibaba.fastjson.JSON;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.ui.ModelMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller基类
 *
 * @author Caesar Liu
 * @date 2021/03/26 19:48
 */
@Slf4j
public class BaseController {

    /**
     * 获取当前登录用户
     *
     * @author Caesar Liu
     * @date 2021-03-28 15:35
     */
    protected LoginUserInfo getLoginUser() {
        return (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 全局异常捕获
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandle(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        try {
            log.error(ex.getMessage(), ex);
            response.setHeader("content-type", "application/json;charset=UTF-8");
            // 业务异常
            if (ex instanceof BusinessException) {
                BusinessException be = (BusinessException) ex;
                response.getWriter().write(JSON.toJSONString(ApiResponse.failed(be.getCode(), be.getMessage())));
                return null;
            }
            // 无权限异常
            if (ex instanceof UnauthorizedException) {
                response.getWriter().write(JSON.toJSONString(ApiResponse.failed("没有操作权限")));
                return null;
            }
            if (ex instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
                // 从异常对象中拿到ObjectError对象
                ObjectError objectError = methodArgumentNotValidException.getBindingResult().getAllErrors().get(0);
                // 然后提取错误提示信息进行返回
                response.getWriter().write(JSON.toJSONString(ApiResponse.failed(CommonConstant.FAIL_CODE, objectError.getDefaultMessage())));
                return null;
            }
            if (ex instanceof BaseException) {
                BaseException baseException = (BaseException) ex;
                response.getWriter().write(JSON.toJSONString(ApiResponse.failed(baseException.getMsg())));
            }
            response.getWriter().write(JSON.toJSONString(ApiResponse.failed(ex.getMessage() == null ? "系统繁忙" : ex.getMessage())));
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ModelMap model = new ModelMap();
            model.addAttribute("error", "未知异常");
            return new ModelAndView("error/default", model);
        }
    }
}