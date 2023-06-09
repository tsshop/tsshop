package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.SeckillGoodsMapper;
import com.ts.shop.domain.SeckillGoods;
import com.ts.shop.service.ISeckillGoodsService;

/**
 * 秒杀活动商品Service业务层处理
 * 
 * @author xu
 * @date 2023-02-21
 */
@Service
public class SeckillGoodsServiceImpl implements ISeckillGoodsService 
{
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    /**
     * 查询秒杀活动商品
     * 
     * @param id 秒杀活动商品主键
     * @return 秒杀活动商品
     */
    @Override
    public SeckillGoods selectSeckillGoodsById(Long id)
    {
        return seckillGoodsMapper.selectSeckillGoodsById(id);
    }

    /**
     * 查询秒杀活动商品列表
     * 
     * @param seckillGoods 秒杀活动商品
     * @return 秒杀活动商品
     */
    @Override
    public List<SeckillGoods> selectSeckillGoodsList(SeckillGoods seckillGoods)
    {
        return seckillGoodsMapper.selectSeckillGoodsList(seckillGoods);
    }

    /**
     * 新增秒杀活动商品
     * 
     * @param seckillGoods 秒杀活动商品
     * @return 结果
     */
    @Override
    public int insertSeckillGoods(SeckillGoods seckillGoods)
    {
        seckillGoods.setCreateTime(DateUtils.getNowDate());
        return seckillGoodsMapper.insertSeckillGoods(seckillGoods);
    }

    /**
     * 修改秒杀活动商品
     * 
     * @param seckillGoods 秒杀活动商品
     * @return 结果
     */
    @Override
    public int updateSeckillGoods(SeckillGoods seckillGoods)
    {
        seckillGoods.setUpdateTime(DateUtils.getNowDate());
        return seckillGoodsMapper.updateSeckillGoods(seckillGoods);
    }

    /**
     * 批量删除秒杀活动商品
     * 
     * @param ids 需要删除的秒杀活动商品主键
     * @return 结果
     */
    @Override
    public int deleteSeckillGoodsByIds(Long[] ids)
    {
        return seckillGoodsMapper.deleteSeckillGoodsByIds(ids);
    }

    /**
     * 删除秒杀活动商品信息
     * 
     * @param id 秒杀活动商品主键
     * @return 结果
     */
    @Override
    public int deleteSeckillGoodsById(Long id)
    {
        return seckillGoodsMapper.deleteSeckillGoodsById(id);
    }
}
