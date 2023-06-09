package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.Seckill;
import com.ts.shop.domain.param.SeckillDto;
import com.ts.shop.domain.vo.SeckillVo;

/**
 * 秒杀活动Service接口
 * 
 * @author xu
 * @date 2023-02-21
 */
public interface ISeckillService 
{
    /**
     * 查询秒杀活动
     * 
     * @param id 秒杀活动主键
     * @return 秒杀活动
     */
    public SeckillVo selectSeckillById(Long id);

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
     * 批量删除秒杀活动
     * 
     * @param ids 需要删除的秒杀活动主键集合
     * @return 结果
     */
    public int deleteSeckillByIds(Long[] ids);

    /**
     * 删除秒杀活动信息
     * 
     * @param id 秒杀活动主键
     * @return 结果
     */
    public int deleteSeckillById(Long id);

    int insert(SeckillDto seckill);

    int update(SeckillDto dto);

    int onOff(SeckillDto dto);
}
