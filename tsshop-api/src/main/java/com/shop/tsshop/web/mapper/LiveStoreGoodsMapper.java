package com.shop.tsshop.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.domain.LiveStoreGoods;
import com.shop.tsshop.web.model.dto.PageDto;
import com.shop.tsshop.web.model.vo.LiveStoreGoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName LiveStoreGoodsMapper
 * @Author TS SHOP
 * @create 2023/5/23
 */

public interface LiveStoreGoodsMapper extends BaseMapper<LiveStoreGoods> {
    /**
     * 查询小店商品列表
     * @param pageDto      dto
     * @param id           小店 ID
     * @return             Vo
     */
    List<LiveStoreGoodsVo> selectGoodsList(@Param("dto") PageDto pageDto,@Param("storeId") Long id);
}