package com.shop.tsshop.config.exception;

import lombok.Data;

/**
 * 自定义异常(CustomException)
 * @author dolyw.com
 * @date 2018/8/30 13:59
 */
@Data
public class CustomException extends RuntimeException {

    private Integer code;

    private String message;

    public CustomException(Integer code,String message){
        this.code = code;
        this.message = message;
    }
    public CustomException(String message){
        this.code = 401;
        this.message = message;
    }
}
