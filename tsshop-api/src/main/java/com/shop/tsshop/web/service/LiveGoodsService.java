package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.LiveGoods;

/**
 * 直播商品表;(live_goods)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-5-25
 */
public interface LiveGoodsService extends IService<LiveGoods>{
    /**
     * 更新直播商品
     * @param liveGoods       直播商品
     */
    void updateLiveGoods(LiveGoods liveGoods);

    /**
     * 删除直播商品
     * @param liveGoodsId     商品 ID
     */
    void delGoods(Long liveGoodsId);

    /**
     * 添加直播销量
     * @param quantity         销量
     * @param liveId           直播 ID
     * @param goodsId          商品 ID
     */
    void addSalesVolume(Integer quantity, Long liveId, Long goodsId);
}
