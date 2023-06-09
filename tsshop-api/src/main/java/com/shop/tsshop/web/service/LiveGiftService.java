package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.LiveGift;
import com.shop.tsshop.web.model.dto.PageDto;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LiveGiftService
 * @Author TsShop
 * @create 2023/5/23
 */

public interface LiveGiftService extends IService<LiveGift>{


    /**
     * 查询礼物列表
     * @param pageDto          dto
     * @return                 统一返回
     */
     ApiResult<Object> giftList(PageDto pageDto);

    /**
     * 用戶礼物列表
     * @param pageDto          dto
     * @param request          request
     * @return                 统一返回
     */
    ApiResult<Object> userGiftInfo(PageDto pageDto, HttpServletRequest request);

    /**
     * 用户礼物购买记录
     * @param pageDto          dto
     * @param request          request
     * @return                 统一返回
     */
    ApiResult<Object> liveGiftBuyRecord(PageDto pageDto, HttpServletRequest request);
}
