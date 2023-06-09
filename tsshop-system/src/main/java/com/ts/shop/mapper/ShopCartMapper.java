package com.ts.shop.mapper;

import java.util.List;
import com.ts.shop.domain.ShopCart;

/**
 * 购物车Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface ShopCartMapper 
{
    /**
     * 查询购物车
     * 
     * @param id 购物车主键
     * @return 购物车
     */
    public ShopCart selectShopCartById(Long id);

    /**
     * 查询购物车列表
     * 
     * @param shopCart 购物车
     * @return 购物车集合
     */
    public List<ShopCart> selectShopCartList(ShopCart shopCart);

    /**
     * 新增购物车
     * 
     * @param shopCart 购物车
     * @return 结果
     */
    public int insertShopCart(ShopCart shopCart);

    /**
     * 修改购物车
     * 
     * @param shopCart 购物车
     * @return 结果
     */
    public int updateShopCart(ShopCart shopCart);

    /**
     * 删除购物车
     * 
     * @param id 购物车主键
     * @return 结果
     */
    public int deleteShopCartById(Long id);

    /**
     * 批量删除购物车
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopCartByIds(Long[] ids);
}
