package com.shop.tsshop.config.exception;

import com.shop.tsshop.config.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常控制处理器
 * @author dolyw.com
 * @date 2018/8/30 14:02
 */
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 捕捉401异常
     * @return
     */
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomException.class)
    public ApiResult<Object> handle(CustomException ex) {
        return ApiResult.result(ex.getCode(), ex.getMessage(),"");
    }

    @ExceptionHandler(MyException.class)
    public ApiResult<Object> handle(MyException ex) {
//        ex.printStackTrace();
        return ApiResult.result(ex.getCode(), ex.getMessage(),"");
    }

    /**
     * 捕捉其他所有异常
     * @param request
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResult<Object> globalException(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        return ApiResult.result(this.getStatus(request).value(), ex.toString() + ": " + ex.getMessage(), null);
    }


    /**
     * 获取状态码
     * @param request
     * @return
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
