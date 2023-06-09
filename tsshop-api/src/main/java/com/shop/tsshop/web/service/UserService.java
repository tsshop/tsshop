package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.UserDto;
import com.shop.tsshop.web.model.dto.UserLoginDto;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-06
 */
public interface UserService extends IService<User> {
    /**
     * 登录
     * @param loginParam                   登录信息
     * @return                             统一返回
     */
    ApiResult<Object> loginByScenario(UserLoginDto loginParam) throws Exception;

    /**
     * 更新密码
     * @param userDto                       dto
     * @return                              obj
     */
    Object updatePassword(UserDto userDto) throws Exception;

    /**
     * 获取微信 Token
     * @return                              token
     */
    String getWxToken();

    /**
     * 获取微信 open Id
     * @param code                          code
     * @return                              统一返回
     */
    ApiResult<Object> getOpenId(String code);
}
