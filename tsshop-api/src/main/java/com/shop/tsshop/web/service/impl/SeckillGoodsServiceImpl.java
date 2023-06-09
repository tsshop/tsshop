package com.shop.tsshop.web.service.impl;

import com.shop.tsshop.web.model.domain.SeckillGoods;
import com.shop.tsshop.web.mapper.SeckillGoodsMapper;
import com.shop.tsshop.web.model.vo.SeckillGoodsVo;
import com.shop.tsshop.web.service.SeckillGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 秒杀活动商品 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-21
 */
@Service
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoods> implements SeckillGoodsService {


    @Override
    public List<SeckillGoodsVo> getGoodsVo(Long seckillId, int time) {

        return baseMapper.selectGoodsVo(seckillId,time);
    }
//k 1 sku,2 商品
    @Override
    public SeckillGoods getGoodsById(Long skuId, int k) {
        return baseMapper.getGoodsById(skuId,k);
    }

    @Override
    public int updateStock(int num, Long id) {
        return baseMapper.updateStock(num,id);
    }

    @Override
    public List<SeckillGoods> getGoodsListById(Long id) {
        return baseMapper.getGoodsListById(id);
    }
}
