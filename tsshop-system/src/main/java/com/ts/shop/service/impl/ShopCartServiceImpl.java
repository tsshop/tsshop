package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.ShopCartMapper;
import com.ts.shop.domain.ShopCart;
import com.ts.shop.service.IShopCartService;

/**
 * 购物车Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class ShopCartServiceImpl implements IShopCartService 
{
    @Autowired
    private ShopCartMapper shopCartMapper;

    /**
     * 查询购物车
     * 
     * @param id 购物车主键
     * @return 购物车
     */
    @Override
    public ShopCart selectShopCartById(Long id)
    {
        return shopCartMapper.selectShopCartById(id);
    }

    /**
     * 查询购物车列表
     * 
     * @param shopCart 购物车
     * @return 购物车
     */
    @Override
    public List<ShopCart> selectShopCartList(ShopCart shopCart)
    {
        return shopCartMapper.selectShopCartList(shopCart);
    }

    /**
     * 新增购物车
     * 
     * @param shopCart 购物车
     * @return 结果
     */
    @Override
    public int insertShopCart(ShopCart shopCart)
    {
        shopCart.setCreateTime(DateUtils.getNowDate());
        return shopCartMapper.insertShopCart(shopCart);
    }

    /**
     * 修改购物车
     * 
     * @param shopCart 购物车
     * @return 结果
     */
    @Override
    public int updateShopCart(ShopCart shopCart)
    {
        shopCart.setUpdateTime(DateUtils.getNowDate());
        return shopCartMapper.updateShopCart(shopCart);
    }

    /**
     * 批量删除购物车
     * 
     * @param ids 需要删除的购物车主键
     * @return 结果
     */
    @Override
    public int deleteShopCartByIds(Long[] ids)
    {
        return shopCartMapper.deleteShopCartByIds(ids);
    }

    /**
     * 删除购物车信息
     * 
     * @param id 购物车主键
     * @return 结果
     */
    @Override
    public int deleteShopCartById(Long id)
    {
        return shopCartMapper.deleteShopCartById(id);
    }
}
