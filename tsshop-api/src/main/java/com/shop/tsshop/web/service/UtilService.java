package com.shop.tsshop.web.service;

import com.aliyuncs.exceptions.ClientException;
import com.shop.tsshop.config.ApiResult;

public interface UtilService {
    /**
     * 发送短信工具类
     * @param phone                        电话
     * @param s                            缓存前缀
     * @return                             统一返回
     */
    ApiResult<Object> sendMessage(String phone, String s) throws ClientException;
}
