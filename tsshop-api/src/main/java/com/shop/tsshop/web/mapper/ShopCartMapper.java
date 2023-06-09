package com.shop.tsshop.web.mapper;

import com.shop.tsshop.web.model.domain.ShopCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.vo.CartListVo;

import java.util.List;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
public interface ShopCartMapper extends BaseMapper<ShopCart> {

    List<CartListVo> getList(Long userId);

    List<CartListVo> getListByIds(List<Long> ids, Long userId);
}
