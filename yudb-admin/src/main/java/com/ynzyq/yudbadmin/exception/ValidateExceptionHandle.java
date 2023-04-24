package com.ynzyq.yudbadmin.exception;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author wj
 * @Date 2021/10/20
 */
@Slf4j
@RestControllerAdvice
public class ValidateExceptionHandle {

    @ExceptionHandler(Exception.class)
    public ApiRes MethodArgumentNotValidExceptionHandler(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            // 从异常对象中拿到ObjectError对象
            ObjectError objectError = methodArgumentNotValidException.getBindingResult().getAllErrors().get(0);
            // 然后提取错误提示信息进行返回
            return new ApiRes(CommonConstant.FAIL_CODE, objectError.getDefaultMessage(), "");
        } else if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            return new ApiRes(baseException.getCode(), baseException.getMsg(), "");
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return new ApiRes(CommonConstant.FAIL_CODE, "不支持该请求方式", "");
        }
        log.error("异常捕获：", e);
        return new ApiRes(CommonConstant.FAIL_CODE, "系统异常", "");
    }
}
