package com.shop.tsshop.web.service;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.LiveStore;
import com.shop.tsshop.web.model.dto.LiveStoreCashOutDto;
import com.shop.tsshop.web.model.dto.LiveStoreOrderListDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @ClassName LiveStoreService
 * @Author TsShop
 * @create 2023/5/23
 */

public interface LiveStoreService extends IService<LiveStore>{


    /**
     * 获取直播小店信息
     * @param request                   request
     * @return                          统一返回
     */
    ApiResult<Object> getInfo(HttpServletRequest request);

    /**
     * 编辑店铺信息
     * @param liveStore                 编辑店铺信息
     * @param request                   request
     * @return                          统一返回
     */
    ApiResult<Object> liveStoreEdit(LiveStore liveStore, HttpServletRequest request);

    /**
     * 直播小店订单
     * @param liveStoreOrderListDto     dto
     * @param request                   request
     * @return                          统一返回
     */
    ApiResult<Object> liveStoreOrderList(LiveStoreOrderListDto liveStoreOrderListDto, HttpServletRequest request);

    /**
     * 获取提现方式
     * @return                          统一返回
     */
    ApiResult<Object> withdrawDepositType();

    /**
     * 获取提现验证码
     * @param request                   request
     */
    ApiResult<Object> getWithdrawCode(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void addProfit(Long liveStoreId, BigDecimal profit);

    /**
     * 店铺提现
     * @param liveStoreCashOutDto       dto
     * @param request                   request
     * @return                          统一返回
     */
    ApiResult<Object> liveStoreCashOut(LiveStoreCashOutDto liveStoreCashOutDto, HttpServletRequest request) throws AlipayApiException;
}
