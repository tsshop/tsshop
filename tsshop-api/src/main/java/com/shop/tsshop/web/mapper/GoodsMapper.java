package com.shop.tsshop.web.mapper;

import com.shop.tsshop.web.model.domain.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.dto.GoodsSearchDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<Goods> getList(GoodsSearchDto dto);

    int updateStock(@Param("num") int num,@Param("id") Long id);

    void updateQuantity(@Param("goodsId") Long goodsId, @Param("quantity") Integer quantity);
}
