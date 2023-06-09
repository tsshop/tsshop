package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.LiveStoreWithdrawalsLog;
import com.shop.tsshop.web.model.dto.PageDto;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LiveStoreWithdrawalsLogService
 * @Author TsShop
 * @create 2023/5/23
 */

public interface LiveStoreWithdrawalsLogService extends IService<LiveStoreWithdrawalsLog>{


    /**
     * 提现记录列表
     * @param pageDto            dto
     * @param request            request
     * @return                   统一返回
     */
    ApiResult<Object> getList(PageDto pageDto, HttpServletRequest request);
}
