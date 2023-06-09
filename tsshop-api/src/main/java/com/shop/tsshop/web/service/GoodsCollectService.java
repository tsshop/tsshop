package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.Goods;
import com.shop.tsshop.web.model.domain.GoodsCollect;

import java.util.List;

/**
 * <p>
 * 商品收藏 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
public interface GoodsCollectService extends IService<GoodsCollect> {

    /**
     * 获取收藏的商品
     * @param id             用户ID
     * @return               统一返回
     */
    List<Goods> getCollect(Long id);
}
