package com.ts.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.shop.domain.LiveStoreGoods;
import com.ts.shop.domain.dto.LiveStoreGoodsListDto;
import com.ts.shop.domain.vo.LiveStoreGoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName LiveStoreGoodsMapper
 * @Author TS SHOP
 * @create 2023/5/29
 */

public interface LiveStoreGoodsMapper extends BaseMapper<LiveStoreGoods> {
    /**
     * 查询小店商品列表
     *
     * @param dto             dto
     * @return Vo
     */
    List<LiveStoreGoodsVo> selectGoodsList(@Param("dto") LiveStoreGoodsListDto dto);
}