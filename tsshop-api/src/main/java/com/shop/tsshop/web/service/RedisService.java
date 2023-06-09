package com.shop.tsshop.web.service;

import com.shop.tsshop.web.model.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface RedisService {



    Object getObj(String key);
    String getString(String key);

    /**
     * 保存值
     *
     * @param key
     * @param val
     */
    void saveCode(String key, Object val, Long time);
    void saveCode(String key, Object val);

    /**
     * delete
     *
     * @param key
     */
    void delete(String key);

    /**
     * 清空所有缓存
     */
    void flushdb();

    /**
     * 保存用户到redis
     * @param key 手机号
     * @param var 用户信息
     */

    void saveUser(String key,Object var);

    /**
     * 根据id查询
     */
    Object selectByUserId(String key);


    /**
     * 获得当前登录用户的用户id
     *
     * @param httpServletRequest 请求体
     * @return 当前登录用户
     */
    User getCurrentUser(HttpServletRequest httpServletRequest);

    Boolean hasKey(String key);

    User getCurrentUser(String token);
}
