package com.shop.tsshop.web.controller;


import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.UserDto;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.service.UserService;
import com.shop.tsshop.web.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-06
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/updateAudi")
    //修改用户名或头像
    public ApiResult<Object> updateAudi(@RequestBody UserDto userDto, HttpServletRequest request) {
        User redis=redisService.getCurrentUser(request);
        User u=new User();
        u.setId(redis.getId());
        if (userDto.getName() != null) {
            if (0 < userDto.getName().length() && 11 > userDto.getName().length()) {
                u.setName(userDto.getName());
            } else {
                return ApiResult.fail("名字长度在1~10之间");
            }
        }
        if (userDto.getAvatarUrl() != null) {
            u.setAvatarUrl(userDto.getAvatarUrl());
        }
        u.setGender(userDto.getGender());
        userService.updateById(u);
        User user = userService.getById(redis.getId());
        user.setToken(redis.getToken());
        redisService.saveUser(String.valueOf(user.getId()), user);
        return ApiResult.ok();
    }

    @PostMapping("/updatePassword")
    //修改密码
    public ApiResult<Object> updatePassword(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
        User redis=redisService.getCurrentUser(request);
        userDto.setId(redis.getId());
        userDto.setPhone(redis.getPhone());
        return ApiResult.ok(userService.updatePassword(userDto));
    }
}
