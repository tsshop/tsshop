package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.LiveStoreApply;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LiveStoreApplyService
 * @Author TsShop
 * @create 2023/5/24
 */

public interface LiveStoreApplyService extends IService<LiveStoreApply>{
    /**
     * 直播小店申请
     * @param liveStoreApply       dto
     * @return                     统一返回
     */
    ApiResult<Object> applyLiveStore(LiveStoreApply liveStoreApply, HttpServletRequest request);
}
