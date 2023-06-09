package com.shop.tsshop.web.controller;

import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.dto.LiveStoreOrderListDto;
import com.shop.tsshop.web.model.dto.PageDto;
import com.shop.tsshop.web.service.LiveGiftDetailService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LiveGiftDetailController
 * @Author TS SHOP
 * @create 2023/5/29
 */
@Slf4j
@RestController
@RequestMapping("/liveGiftDetail")
public class LiveGiftDetailController {

    @Autowired
    LiveGiftDetailService liveGiftDetailService;

    @PostMapping("/list")
    public ApiResult<Object> liveGiftDetailList(@RequestBody PageDto pageDto, HttpServletRequest request) {
        return liveGiftDetailService.liveGiftDetailList(pageDto,request);
    }
}
