package com.shop.tsshop.web.service.impl;

import com.shop.tsshop.web.model.domain.GoodsType;
import com.shop.tsshop.web.mapper.GoodsTypeMapper;
import com.shop.tsshop.web.service.GoodsTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品类型表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {

    @Override
    public Object getType() {
        return baseMapper.getType();
    }
}
