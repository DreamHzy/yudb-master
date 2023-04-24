package com.yunzyq.yudbapp.api;

import com.alibaba.fastjson.JSON;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
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
                response.getWriter().write(JSON.toJSONString(ApiRes.failed(be.getCode(), be.getMessage())));
                return null;
            }
            response.getWriter().write(JSON.toJSONString(ApiRes.failed(ex.getMessage() == null ? "系统繁忙" : ex.getMessage())));
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ModelMap model = new ModelMap();
            model.addAttribute("error", "未知异常");
            return new ModelAndView("error/default", model);
        }
    }
}