package com.shop.tsshop.web.controller;


import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 配置 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    ConfigService configService;

    @GetMapping("configList")
    //获取config列表
    @PassToken
    public ApiResult<Object> configList() {

        return ApiResult.ok(configService.getConfigList());
    }
}
