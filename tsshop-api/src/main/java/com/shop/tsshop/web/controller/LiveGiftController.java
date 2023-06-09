package com.shop.tsshop.web.controller;

import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.LiveGoods;
import com.shop.tsshop.web.model.dto.PageDto;
import com.shop.tsshop.web.service.LiveGiftService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LiveGiftController
 * @Author TS SHOP
 * @create 2023/5/29
 */
@Slf4j
@RestController
@RequestMapping("/liveGift")
public class LiveGiftController {

    @Autowired
    LiveGiftService liveGiftService;

    @PostMapping("/list")
    public ApiResult<Object> liveGiftList(@RequestBody PageDto pageDto){
        return liveGiftService.giftList(pageDto);
    }

    @PostMapping("/userGift")
    public ApiResult<Object> userGiftInfo(@RequestBody PageDto pageDto, HttpServletRequest request){
        return liveGiftService.userGiftInfo(pageDto,request);
    }

    @PostMapping("/buyRecord")
    public ApiResult<Object> liveGiftBuyRecord(@RequestBody PageDto pageDto, HttpServletRequest request){
        return liveGiftService.liveGiftBuyRecord(pageDto,request);
    }


}
