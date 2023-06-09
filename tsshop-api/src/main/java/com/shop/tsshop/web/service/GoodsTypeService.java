package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.GoodsType;

/**
 * <p>
 * 商品类型表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
public interface GoodsTypeService extends IService<GoodsType> {

    /**
     * 获取商品类型
     * @return            商品类型
     */
    Object getType();
}
