package com.shop.tsshop.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.domain.LiveGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 直播商品表;(live_goods)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-5-25
 */
@Mapper
public interface LiveGoodsMapper  extends BaseMapper<LiveGoods>{
    @Update(" update live_goods set sales_volume = sales_volume + #{quantity} where live_id = #{liveId} and goods_id = #{goodsId} ")
    void addSalesVolume(@Param("quantity") Integer quantity,@Param("liveId") Long liveId,@Param("goodsId") Long goodsId);
}
