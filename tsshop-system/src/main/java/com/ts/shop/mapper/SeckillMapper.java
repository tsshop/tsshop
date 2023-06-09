package com.ts.shop.mapper;

import java.util.List;
import com.ts.shop.domain.Seckill;

/**
 * 秒杀活动Mapper接口
 * 
 * @author xu
 * @date 2023-02-21
 */
public interface SeckillMapper 
{
    /**
     * 查询秒杀活动
     * 
     * @param id 秒杀活动主键
     * @return 秒杀活动
     */
    public Seckill selectSeckillById(Long id);

    /**
     * 查询秒杀活动列表
     * 
     * @param seckill 秒杀活动
     * @return 秒杀活动集合
     */
    public List<Seckill> selectSeckillList(Seckill seckill);

    /**
     * 新增秒杀活动
     * 
     * @param seckill 秒杀活动
     * @return 结果
     */
    public int insertSeckill(Seckill seckill);

    /**
     * 修改秒杀活动
     * 
     * @param seckill 秒杀活动
     * @return 结果
     */
    public int updateSeckill(Seckill seckill);

    /**
     * 删除秒杀活动
     * 
     * @param id 秒杀活动主键
     * @return 结果
     */
    public int deleteSeckillById(Long id);

    /**
     * 批量删除秒杀活动
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSeckillByIds(Long[] ids);

    void updateStatus();
}
