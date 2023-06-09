package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.Goods;
import com.ts.shop.domain.param.GoodsUpdateDTO;

/**
 * 商品Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IGoodsService 
{
    /**
     * 查询商品
     * 
     * @param id 商品主键
     * @return 商品
     */
    public Goods selectGoodsById(Long id);

    /**
     * 查询商品列表
     * 
     * @param goods 商品
     * @return 商品集合
     */
    public List<Goods> selectGoodsList(Goods goods);

    /**
     * 新增商品
     * 
     * @param goods 商品
     * @return 结果
     */
    public int insertGoods(Goods goods);

    /**
     * 修改商品
     * 
     * @param goods 商品
     * @return 结果
     */
    public int updateGoods(Goods goods);

    /**
     * 批量删除商品
     * 
     * @param ids 需要删除的商品主键集合
     * @return 结果
     */
    public int deleteGoodsByIds(Long[] ids);

    /**
     * 删除商品信息
     * 
     * @param id 商品主键
     * @return 结果
     */
    public int deleteGoodsById(Long id);

    int create(GoodsUpdateDTO addUpdateDTO);

    /**
     * 统计商品数量
     * @return         商品数量
     */
    Long countGoodsNum();
}
