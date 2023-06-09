package com.shop.tsshop.web.mapper;

import com.shop.tsshop.web.model.domain.Goods;
import com.shop.tsshop.web.model.domain.GoodsCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 商品收藏 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
public interface GoodsCollectMapper extends BaseMapper<GoodsCollect> {

    List<Goods> getCollect(Long userId);
}
