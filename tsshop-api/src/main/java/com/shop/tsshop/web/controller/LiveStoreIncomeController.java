package com.shop.tsshop.web.controller;

import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.LiveStoreIncomeLog;
import com.shop.tsshop.web.model.dto.LiveStoreIncomeCountDto;
import com.shop.tsshop.web.model.dto.LiveStoreIncomeListDto;
import com.shop.tsshop.web.model.dto.PageDto;
import com.shop.tsshop.web.service.LiveStoreIncomeLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LiveStoreIncomeController
 * @Author TS SHOP
 * @create 2023/5/25
 */
@RestController
@Slf4j
@RequestMapping("/liveStoreIncome")
public class LiveStoreIncomeController {

    @Autowired
    LiveStoreIncomeLogService liveStoreIncomeLogService;

    @PostMapping("/list")
    public ApiResult<Object> liveStoreIncome(@RequestBody LiveStoreIncomeListDto liveStoreIncomeListDto, HttpServletRequest request) {
        return liveStoreIncomeLogService.liveStoreIncomeInfo(liveStoreIncomeListDto,request);
    }

    @PostMapping("/count")
    public ApiResult<Object> liveStoreIncomeCount(@RequestBody LiveStoreIncomeCountDto liveStoreIncomeListDto, HttpServletRequest request) {
        return liveStoreIncomeLogService.liveStoreIncomeCount(liveStoreIncomeListDto,request);
    }

}
