package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.LiveStoreIncomeLog;
import com.shop.tsshop.web.model.dto.LiveStoreIncomeCountDto;
import com.shop.tsshop.web.model.dto.LiveStoreIncomeListDto;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @ClassName LiveStoreIncomeLogService
 * @Author TsShop
 * @create 2023/5/23
 */

public interface LiveStoreIncomeLogService extends IService<LiveStoreIncomeLog>{

    /**
     * 收益明细
     * @param liveStoreIncomeListDto           dto
     * @param request                          request
     * @return                                 统一返回
     */
    ApiResult<Object> liveStoreIncomeInfo(LiveStoreIncomeListDto liveStoreIncomeListDto, HttpServletRequest request);

    /**
     * 统计收益信息
     * @param liveStoreIncomeListDto           dto
     * @param request                          request
     * @return                                 统一返回
     */
    ApiResult<Object> liveStoreIncomeCount(LiveStoreIncomeCountDto liveStoreIncomeListDto, HttpServletRequest request);

    void create(Long liveStoreId, BigDecimal amt,Integer incomeType);
}
