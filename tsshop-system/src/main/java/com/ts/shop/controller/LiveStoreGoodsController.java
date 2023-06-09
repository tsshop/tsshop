package com.ts.shop.controller;

import com.ts.common.core.domain.AjaxResult;
import com.ts.shop.domain.dto.LiveStoreGoodsListDto;
import com.ts.shop.service.LiveStoreGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LiveStoreGoodsController
 * @Author TS SHOP
 * @create 2023/5/29
 */
@RestController
@Slf4j
@RequestMapping("/liveStoreGoods")
public class LiveStoreGoodsController {
    @Autowired
    LiveStoreGoodsService liveStoreGoodsService;


    @PostMapping("/list")
    public AjaxResult liveStoreGoodsList(@RequestBody LiveStoreGoodsListDto liveStoreGoodsListDto) {
        return liveStoreGoodsService.liveStoreGoodsList(liveStoreGoodsListDto);
    }
}
