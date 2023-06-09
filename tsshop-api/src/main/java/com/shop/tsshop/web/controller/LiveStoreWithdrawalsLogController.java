package com.shop.tsshop.web.controller;

import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.LiveStoreWithdrawalsLog;
import com.shop.tsshop.web.model.dto.LiveStoreOrderListDto;
import com.shop.tsshop.web.model.dto.PageDto;
import com.shop.tsshop.web.service.LiveStoreWithdrawalsLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LiveStoreWithdrawalsLogController
 * @Author TS SHOP
 * @create 2023/5/30
 */
@RestController
@Slf4j
@RequestMapping("/liveStoreWithdrawalsLog")
public class LiveStoreWithdrawalsLogController {

    @Autowired
    LiveStoreWithdrawalsLogService liveStoreWithdrawalsLogService;

    @PostMapping("/list")
    public ApiResult<Object> liveStoreOrderList(@RequestBody PageDto pageDto, HttpServletRequest request) {
        return liveStoreWithdrawalsLogService.getList(pageDto,request);
    }
}
