package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.GoodsCollectMapper;
import com.ts.shop.domain.GoodsCollect;
import com.ts.shop.service.IGoodsCollectService;

/**
 * 商品收藏Service业务层处理
 * 
 * @author xu
 * @date 2023-02-14
 */
@Service
public class GoodsCollectServiceImpl implements IGoodsCollectService 
{
    @Autowired
    private GoodsCollectMapper goodsCollectMapper;

    /**
     * 查询商品收藏
     * 
     * @param id 商品收藏主键
     * @return 商品收藏
     */
    @Override
    public GoodsCollect selectGoodsCollectById(Long id)
    {
        return goodsCollectMapper.selectGoodsCollectById(id);
    }

    /**
     * 查询商品收藏列表
     * 
     * @param goodsCollect 商品收藏
     * @return 商品收藏
     */
    @Override
    public List<GoodsCollect> selectGoodsCollectList(GoodsCollect goodsCollect)
    {
        return goodsCollectMapper.selectGoodsCollectList(goodsCollect);
    }

    /**
     * 新增商品收藏
     * 
     * @param goodsCollect 商品收藏
     * @return 结果
     */
    @Override
    public int insertGoodsCollect(GoodsCollect goodsCollect)
    {
        goodsCollect.setCreateTime(DateUtils.getNowDate());
        return goodsCollectMapper.insertGoodsCollect(goodsCollect);
    }

    /**
     * 修改商品收藏
     * 
     * @param goodsCollect 商品收藏
     * @return 结果
     */
    @Override
    public int updateGoodsCollect(GoodsCollect goodsCollect)
    {
        goodsCollect.setUpdateTime(DateUtils.getNowDate());
        return goodsCollectMapper.updateGoodsCollect(goodsCollect);
    }

    /**
     * 批量删除商品收藏
     * 
     * @param ids 需要删除的商品收藏主键
     * @return 结果
     */
    @Override
    public int deleteGoodsCollectByIds(Long[] ids)
    {
        return goodsCollectMapper.deleteGoodsCollectByIds(ids);
    }

    /**
     * 删除商品收藏信息
     * 
     * @param id 商品收藏主键
     * @return 结果
     */
    @Override
    public int deleteGoodsCollectById(Long id)
    {
        return goodsCollectMapper.deleteGoodsCollectById(id);
    }
}
