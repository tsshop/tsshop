package com.shop.tsshop.web.service.impl;

import com.shop.tsshop.web.model.domain.Goods;
import com.shop.tsshop.web.model.domain.GoodsCollect;
import com.shop.tsshop.web.mapper.GoodsCollectMapper;
import com.shop.tsshop.web.service.GoodsCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品收藏 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
@Service
public class GoodsCollectServiceImpl extends ServiceImpl<GoodsCollectMapper, GoodsCollect> implements GoodsCollectService {

    @Override
    public List<Goods> getCollect(Long userId) {
        return baseMapper.getCollect(userId);
    }
}
