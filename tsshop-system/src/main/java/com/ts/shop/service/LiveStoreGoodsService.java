package com.ts.shop.service;

import com.ts.common.core.domain.AjaxResult;
import com.ts.shop.domain.LiveStoreGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.shop.domain.dto.LiveStoreGoodsListDto;

/**
 * @ClassName LiveStoreGoodsService
 * @Author TS SHOP
 * @create 2023/5/29
 */

public interface LiveStoreGoodsService extends IService<LiveStoreGoods>{


    /**
     * 获取商品列表
     * @param liveStoreGoodsListDto            dto
     * @return                                 统一返回
     */
    AjaxResult liveStoreGoodsList(LiveStoreGoodsListDto liveStoreGoodsListDto);
}
