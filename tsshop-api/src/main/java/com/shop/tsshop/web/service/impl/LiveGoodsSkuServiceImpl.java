package com.shop.tsshop.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.mapper.LiveGoodsSkuMapper;
import com.shop.tsshop.web.model.domain.LiveGoodsSku;
import com.shop.tsshop.web.service.LiveGoodsSkuService;
import org.springframework.stereotype.Service;

/**
 * 直播商品信息表;(live_goods_sku)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-5-25
 */
@Service
public class LiveGoodsSkuServiceImpl extends ServiceImpl<LiveGoodsSkuMapper, LiveGoodsSku> implements LiveGoodsSkuService {

    @Override
    public Integer updateStock(int num, Long id) {
        return baseMapper.updateStock(num,id);
    }
}
