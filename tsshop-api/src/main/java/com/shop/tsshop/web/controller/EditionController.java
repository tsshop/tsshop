package com.shop.tsshop.web.controller;


import com.github.pagehelper.PageHelper;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.service.DeliveryService;
import com.shop.tsshop.web.service.EditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 版本表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
@RestController
@RequestMapping("/edition")
public class EditionController {
    @Autowired
    private EditionService editionService;
    //查看列表
    @GetMapping("/getEdition")
    @PassToken
    public ApiResult<Object> getEdition(Integer type) {
        return ApiResult.ok(editionService.getEdition(type));
    }
}
