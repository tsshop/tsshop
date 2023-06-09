package com.shop.tsshop.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.domain.LiveGoodsSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 直播商品信息表;(live_goods_sku)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-5-25
 */
@Mapper
public interface LiveGoodsSkuMapper  extends BaseMapper<LiveGoodsSku>{

    @Update("update live_goods_sku set stocks = stocks - #{num} where id = #{id}")
    Integer updateStock(int num, Long id);
}
