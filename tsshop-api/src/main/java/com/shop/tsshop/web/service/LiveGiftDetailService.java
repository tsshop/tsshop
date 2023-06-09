package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.LiveGiftDetail;
import com.shop.tsshop.web.model.dto.PageDto;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LiveGiftDetailService
 * @Author TsShop
 * @create 2023/5/23
 */

public interface LiveGiftDetailService extends IService<LiveGiftDetail>{


    /**
     * 礼物赠送记录列表
     * @param pageDto          dto
     * @param request          request
     * @return                 统一返回
     */
    ApiResult<Object> liveGiftDetailList(PageDto pageDto, HttpServletRequest request);
}
