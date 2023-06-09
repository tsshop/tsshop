package com.shop.tsshop.web.controller;


import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.model.domain.Banner;
import com.shop.tsshop.web.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户首页轮播图片表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @GetMapping
    @PassToken
    //"轮播图列表")
    public ApiResult<Object> banner() {
        List<Banner> list = bannerService.getBanner();
        return ApiResult.ok(list);
    }
}
