package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.GoodsCollect;

/**
 * 商品收藏Service接口
 * 
 * @author xu
 * @date 2023-02-14
 */
public interface IGoodsCollectService 
{
    /**
     * 查询商品收藏
     * 
     * @param id 商品收藏主键
     * @return 商品收藏
     */
    public GoodsCollect selectGoodsCollectById(Long id);

    /**
     * 查询商品收藏列表
     * 
     * @param goodsCollect 商品收藏
     * @return 商品收藏集合
     */
    public List<GoodsCollect> selectGoodsCollectList(GoodsCollect goodsCollect);

    /**
     * 新增商品收藏
     * 
     * @param goodsCollect 商品收藏
     * @return 结果
     */
    public int insertGoodsCollect(GoodsCollect goodsCollect);

    /**
     * 修改商品收藏
     * 
     * @param goodsCollect 商品收藏
     * @return 结果
     */
    public int updateGoodsCollect(GoodsCollect goodsCollect);

    /**
     * 批量删除商品收藏
     * 
     * @param ids 需要删除的商品收藏主键集合
     * @return 结果
     */
    public int deleteGoodsCollectByIds(Long[] ids);

    /**
     * 删除商品收藏信息
     * 
     * @param id 商品收藏主键
     * @return 结果
     */
    public int deleteGoodsCollectById(Long id);
}
