package com.shop.tsshop.web.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.mapper.LiveGoodsMapper;
import com.shop.tsshop.web.model.domain.LiveGoods;
import com.shop.tsshop.web.model.domain.LiveGoodsSku;
import com.shop.tsshop.web.service.LiveGoodsService;
import com.shop.tsshop.web.service.LiveGoodsSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 直播商品表;(live_goods)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-5-25
 */
@Service
public class LiveGoodsServiceImpl extends ServiceImpl<LiveGoodsMapper, LiveGoods> implements LiveGoodsService {

    @Autowired
    private LiveGoodsSkuService liveGoodsSkuService;

    @Override
    public void updateLiveGoods(LiveGoods liveGoods) {
        saveOrUpdate(liveGoods);
        liveGoodsSkuService.saveOrUpdateBatch(liveGoods.getGoodsSkuList());
    }

    @Override
    public void delGoods(Long liveGoodsId) {
        LiveGoods liveGoods = getById(liveGoodsId);
        if (ObjectUtil.isEmpty(liveGoods)){
            return;
        }
        removeById(liveGoodsId);
        liveGoodsSkuService.remove(new LambdaQueryWrapper<LiveGoodsSku>()
                .eq(LiveGoodsSku::getLiveGoodsId, liveGoodsId)
                .eq(LiveGoodsSku::getLiveId,liveGoods.getLiveId()));
    }

    @Override
    public void addSalesVolume(Integer quantity, Long liveId, Long goodsId) {
        baseMapper.addSalesVolume(quantity, liveId,goodsId);
    }
}
