package com.ts.shop.mapper;

import java.util.List;
import com.ts.shop.domain.SeckillGoods;
import com.ts.shop.domain.vo.SeckillGoodsVo;

/**
 * 秒杀活动商品Mapper接口
 * 
 * @author xu
 * @date 2023-02-21
 */
public interface SeckillGoodsMapper 
{
    /**
     * 查询秒杀活动商品
     * 
     * @param id 秒杀活动商品主键
     * @return 秒杀活动商品
     */
    public SeckillGoods selectSeckillGoodsById(Long id);

    /**
     * 查询秒杀活动商品列表
     * 
     * @param seckillGoods 秒杀活动商品
     * @return 秒杀活动商品集合
     */
    public List<SeckillGoods> selectSeckillGoodsList(SeckillGoods seckillGoods);

    /**
     * 新增秒杀活动商品
     * 
     * @param seckillGoods 秒杀活动商品
     * @return 结果
     */
    public int insertSeckillGoods(SeckillGoods seckillGoods);

    /**
     * 修改秒杀活动商品
     * 
     * @param seckillGoods 秒杀活动商品
     * @return 结果
     */
    public int updateSeckillGoods(SeckillGoods seckillGoods);

    /**
     * 删除秒杀活动商品
     * 
     * @param id 秒杀活动商品主键
     * @return 结果
     */
    public int deleteSeckillGoodsById(Long id);

    /**
     * 批量删除秒杀活动商品
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSeckillGoodsByIds(Long[] ids);

    int delBySecId(Long id);

    List<SeckillGoodsVo> selectSeckillGoodsVoList(SeckillGoods s);
}
