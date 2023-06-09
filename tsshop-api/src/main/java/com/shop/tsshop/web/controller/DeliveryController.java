package com.shop.tsshop.web.controller;


import com.github.pagehelper.PageHelper;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 物流公司 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;
    //查看列表
    @GetMapping("/getList")
    public ApiResult<Object> getList() {
//        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        return ApiResult.ok(deliveryService.list());
    }
}
