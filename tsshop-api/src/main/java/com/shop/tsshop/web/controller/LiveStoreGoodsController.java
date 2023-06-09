package com.shop.tsshop.web.controller;

import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.dto.EditLiveStoreGoodsDto;
import com.shop.tsshop.web.model.dto.LiveStoreAddGoodsDto;
import com.shop.tsshop.web.model.dto.LiveStoreDelGoodsDto;
import com.shop.tsshop.web.model.dto.PageDto;
import com.shop.tsshop.web.service.LiveStoreGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LiveStoreGoodsController
 * @Author TS SHOP
 * @create 2023/5/25
 */
@RestController
@Slf4j
@RequestMapping("/liveStoreGoods")
public class LiveStoreGoodsController {

    @Autowired
    LiveStoreGoodsService liveStoreGoodsService;


    @PostMapping("/list")
    public ApiResult<Object> liveStoreGoodsList(@RequestBody PageDto pageDto, HttpServletRequest request) {
        return liveStoreGoodsService.liveStoreGoodsList(pageDto,request);
    }

    @PostMapping("/addGoods")
    public ApiResult<Object> liveStoreAddGoods(@RequestBody LiveStoreAddGoodsDto liveStoreAddGoodsDto,HttpServletRequest request) {
        return liveStoreGoodsService.addGoods(liveStoreAddGoodsDto,request);
    }

    @GetMapping("/info")
    public ApiResult<Object> getGoodsInfo(Long goodsId,HttpServletRequest request) {
        return liveStoreGoodsService.getGoodsInfo(goodsId,request);
    }

    @PostMapping("/editGoods")
    public ApiResult<Object> liveStoreEditGoods(@RequestBody EditLiveStoreGoodsDto editLiveStoreGoodsDto,HttpServletRequest request) {
        return liveStoreGoodsService.editGoods(editLiveStoreGoodsDto,request);
    }

    @PostMapping("/del")
    public ApiResult<Object> liveStoreDelGoods(@RequestBody LiveStoreDelGoodsDto liveStoreAddGoodsDto, HttpServletRequest request) {
        return liveStoreGoodsService.liveStoreDelGoods(liveStoreAddGoodsDto,request);
    }
}
