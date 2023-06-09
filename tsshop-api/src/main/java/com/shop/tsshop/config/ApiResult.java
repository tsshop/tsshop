package com.shop.tsshop.config;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.tsshop.config.exception.ApiCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * REST API 返回结果
 * </p>
 *
 * @since 2020-10-16
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1762924401215256839L;

    private int status;

    private T data;

    private String msg;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;

    public ApiResult() {

    }
    public ApiResult(int status ,String msg,T data) {
        this.status=status;
        this.data=data;
        this.msg=msg;
        this.time=new Date();
    }
    public static <T> ApiResult<T> result(int status ,String msg,T data){
        return new ApiResult<T>(status,msg,data);
    }
    public static <T> ApiResult<T> ok(T data){
        return new ApiResult<T>(200,"ok",data);
    }
    public static <T> ApiResult<T> ok(){
        return new ApiResult<T>(200,"ok",null);
    }
    public static <T> ApiResult<T> fail(String msg){
        return new ApiResult<T>(500,msg,null);
    }
    public static <T> ApiResult<T> fail(ApiCode apiCode){
        return new ApiResult<T>(apiCode.getCode(), apiCode.getMsg(), null);
    }
}