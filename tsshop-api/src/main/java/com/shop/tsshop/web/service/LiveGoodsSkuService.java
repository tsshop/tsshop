package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.LiveGoodsSku;

/**
 * 直播商品信息表;(live_goods_sku)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-5-25
 */
public interface LiveGoodsSkuService extends IService<LiveGoodsSku>{
    /**
     * 更新商品库存
     * @param num           数量
     * @param id            商品 ID
     * @return              数量
     */
    Integer updateStock(int num, Long id);
}
