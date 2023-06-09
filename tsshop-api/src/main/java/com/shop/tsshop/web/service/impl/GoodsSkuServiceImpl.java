package com.shop.tsshop.web.service.impl;

import com.shop.tsshop.web.model.domain.GoodsSku;
import com.shop.tsshop.web.mapper.GoodsSkuMapper;
import com.shop.tsshop.web.service.GoodsSkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 单品SKU表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Service
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuMapper, GoodsSku> implements GoodsSkuService {

    @Override
    public int updateStock(int num, Long id) {
        return baseMapper.updateStock(num,id);
    }
}
