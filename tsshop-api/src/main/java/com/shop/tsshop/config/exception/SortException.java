package com.shop.tsshop.config.exception;

import lombok.Data;

@Data
public class SortException extends RuntimeException {
    private Integer code;

    private String message;

    public SortException(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    public SortException(String message){
        this.code = 500;
        this.message = message;
    }
    public SortException(ApiCode apiCode){
        this.code = apiCode.getCode();
        this.message = apiCode.getMsg();
    }
}
