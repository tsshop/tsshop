package com.shop.tsshop.web.mapper;

import com.shop.tsshop.web.model.domain.GoodsType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.vo.GoodsTypeVo;

import java.util.List;

/**
 * <p>
 * 商品类型表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
public interface GoodsTypeMapper extends BaseMapper<GoodsType> {

    List<GoodsTypeVo> getType();
}
