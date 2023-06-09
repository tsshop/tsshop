package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.ShopCart;
import com.shop.tsshop.web.model.vo.CartListVo;

import java.util.List;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
public interface ShopCartService extends IService<ShopCart> {

    /**
     * 获取购物车信息
     * @param userId              用户 id
     * @return                    统一返回
     */
    List<CartListVo> getList(Long  userId);

    /**
     * 添加购物车商品
     * @param cart                购物车
     * @return                    obj
     */
    Object addCart(ShopCart cart);

    /**
     * 结算
     * @param ids                 ids
     * @param id                  id
     * @param addressId           地址
     * @return                    obj
     */
    Object settle(List<Long> ids, Long id, Long addressId);

    /**
     * 创建订单
     * @param ids                  ids
     * @param userId               用户 ID
     * @param addressId            地址
     * @param isPass               isPass
     * @return                     obj
     */
    Object createOrder(List<Long> ids, Long userId, Long addressId,Integer isPass);
}
