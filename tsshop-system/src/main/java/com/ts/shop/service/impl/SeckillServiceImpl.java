package com.ts.shop.service.impl;

import java.util.*;

import com.ts.common.exception.ServiceException;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.SeckillGoods;
import com.ts.shop.domain.param.SeckillDto;
import com.ts.shop.domain.vo.SeckillGoodsVo;
import com.ts.shop.domain.vo.SeckillVo;
import com.ts.shop.mapper.SeckillGoodsMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.SeckillMapper;
import com.ts.shop.domain.Seckill;
import com.ts.shop.service.ISeckillService;

/**
 * 秒杀活动Service业务层处理
 * 
 * @author xu
 * @date 2023-02-21
 */
@Service
public class SeckillServiceImpl implements ISeckillService 
{
    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;

    /**
     * 查询秒杀活动
     * 
     * @param id 秒杀活动主键
     * @return 秒杀活动
     */
    @Override
    public SeckillVo selectSeckillById(Long id)
    {
        Seckill seckill=seckillMapper.selectSeckillById(id);
        SeckillVo vo=new SeckillVo();
        BeanUtils.copyProperties(seckill,vo);

        Map<String,List<SeckillGoodsVo>> map=new HashMap<>();

        if(seckill.getTimeInterval()==null){
            return vo;
        }
        String[] timeList = seckill.getTimeInterval().split(",");
        for(String time:timeList){
            int t=Integer.parseInt(time);
            SeckillGoods s=new SeckillGoods();
            s.setSeckillId(seckill.getId());
            s.setTime(t);
            List<SeckillGoodsVo> goods=seckillGoodsMapper.selectSeckillGoodsVoList(s);
            map.put(time,goods);
        }
        vo.setTimeGoodsList(map);
        return vo;
    }

    /**
     * 查询秒杀活动列表
     * 
     * @param seckill 秒杀活动
     * @return 秒杀活动
     */
    @Override
    public List<Seckill> selectSeckillList(Seckill seckill)
    {
        return seckillMapper.selectSeckillList(seckill);
    }

    /**
     * 新增秒杀活动
     * 
     * @param seckill 秒杀活动
     * @return 结果
     */
    @Override
    public int insertSeckill(Seckill seckill)
    {
        seckill.setCreateTime(DateUtils.getNowDate());

        return seckillMapper.insertSeckill(seckill);
    }

    /**
     * 修改秒杀活动
     * 
     * @param seckill 秒杀活动
     * @return 结果
     */
    @Override
    public int updateSeckill(Seckill seckill)
    {
        seckill.setUpdateTime(DateUtils.getNowDate());
        return seckillMapper.updateSeckill(seckill);
    }

    /**
     * 批量删除秒杀活动
     * 
     * @param ids 需要删除的秒杀活动主键
     * @return 结果
     */
    @Override
    public int deleteSeckillByIds(Long[] ids)
    {
        return seckillMapper.deleteSeckillByIds(ids);
    }

    /**
     * 删除秒杀活动信息
     * 
     * @param id 秒杀活动主键
     * @return 结果
     */
    @Override
    public int deleteSeckillById(Long id)
    {
        return seckillMapper.deleteSeckillById(id);
    }

    @Override
    public int insert(SeckillDto dto) {
        Seckill seckill=new Seckill();
        BeanUtils.copyProperties(dto,seckill);
        if(seckill.getStartTime().before(seckill.getUpdateEndTime())){
            throw new ServiceException("修改截止时间不可在活动开始时间之后",500);
        }
        seckill.setStatus(0);
        seckillMapper.insertSeckill(seckill);
        if(dto.getTimeInterval()==null){
            return 1;
        }
        String[] timeList = dto.getTimeInterval().split(",");
        for(String time:timeList){
            int t=Integer.parseInt(time);
            if(t<0 || t>23){
                continue;
            }
            List<SeckillGoods> goods=dto.getTimeGoodsList().get(time);
            if(goods==null || goods.size()==0){
                continue;
            }
            for(SeckillGoods good : goods){
                good.setTime(t);
                good.setSeckillId(seckill.getId());
                if(good.getSkuId()==null){
                    good.setSkuId(0L);
                }
                try {
                    seckillGoodsMapper.insertSeckillGoods(good);
                }catch (Exception ignored){
                }
            }
        }
        return 1;
    }

    @Override
    public int update(SeckillDto dto) {
        Seckill seckill=seckillMapper.selectSeckillById(dto.getId());
        if(System.currentTimeMillis()>seckill.getUpdateEndTime().getTime()){
            throw new ServiceException("已超过最大修改时限，不可修改！",500);
        }
        seckillGoodsMapper.delBySecId(seckill.getId());
        BeanUtils.copyProperties(dto,seckill);

        if(seckill.getStartTime().before(seckill.getUpdateEndTime())){
            throw new ServiceException("修改截止时间不可在活动开始时间之后",500);
        }
        seckill.setStatus(null);
        seckillMapper.updateSeckill(seckill);

        String[] timeList = dto.getTimeInterval().split(",");
        for(String time:timeList){
            int t=Integer.parseInt(time);
            if(t<0 || t>23){
                continue;
            }
            List<SeckillGoods> goods=dto.getTimeGoodsList().get(time);
            if(goods==null || goods.size()==0){
                continue;
            }
            for(SeckillGoods good : goods){
                good.setTime(t);
                good.setSeckillId(seckill.getId());
                if(good.getSkuId()==null){
                    good.setSkuId(0L);
                }
                try {
                    seckillGoodsMapper.insertSeckillGoods(good);
                }catch (Exception ignored){
                }
            }
        }
        return 1;
    }

    @Override
    public int onOff(SeckillDto dto) {
        Integer status = dto.getStatus();
        if(status ==1){
            seckillMapper.updateStatus();
        }
        Seckill seckill =new Seckill();
        seckill.setStatus(dto.getStatus());
        seckill.setId(dto.getId());
        seckillMapper.updateSeckill(seckill);
        return 0;
    }
}
