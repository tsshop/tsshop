package com.shop.tsshop.web.mapper;

import com.shop.tsshop.web.model.domain.GoodsSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 单品SKU表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Mapper
public interface GoodsSkuMapper extends BaseMapper<GoodsSku> {

    int updateStock(@Param("num") int num, @Param("id") Long id);

    @Select("select * from goods_sku where id = #{skuId} for update")
    GoodsSku lock(@Param("skuId") Long skuId);
}
