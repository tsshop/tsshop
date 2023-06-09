package com.shop.tsshop.web.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alipay.api.AlipayApiException;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.enums.PayEnums;
import com.shop.tsshop.web.model.domain.LiveStore;
import com.shop.tsshop.web.model.domain.LiveStoreApply;
import com.shop.tsshop.web.model.domain.pay.PayTypeVo;
import com.shop.tsshop.web.model.dto.*;
import com.shop.tsshop.web.service.LiveStoreService;
import com.shop.tsshop.web.util.PayUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LiveStoreController
 * @Author TS SHOP
 * @create 2023/5/24
 */
@RestController
@Slf4j
@RequestMapping("/liveStore")
public class LiveStoreController {

    @Autowired
    LiveStoreService liveStoreService;

    @GetMapping("/info")
    public ApiResult<Object> getLiveStoreInfo(HttpServletRequest request) {
        return liveStoreService.getInfo(request);
    }

    @PostMapping("/edit")
    public ApiResult<Object> liveStoreEdit(@RequestBody LiveStore liveStore,HttpServletRequest request) {
        return liveStoreService.liveStoreEdit(liveStore,request);
    }

    @PostMapping("/orderList")
    public ApiResult<Object> liveStoreOrderList(@RequestBody LiveStoreOrderListDto liveStoreOrderListDto, HttpServletRequest request) {
        return liveStoreService.liveStoreOrderList(liveStoreOrderListDto,request);
    }

    @GetMapping("/getWithdrawCode")
    public ApiResult<Object> getWithdrawCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return liveStoreService.getWithdrawCode(request,response);
    }

    @PostMapping("/cashOut")
    public ApiResult<Object> liveStoreCashOut(@RequestBody LiveStoreCashOutDto liveStoreCashOutDto, HttpServletRequest request) throws AlipayApiException {
        return liveStoreService.liveStoreCashOut(liveStoreCashOutDto,request);
    }

}
