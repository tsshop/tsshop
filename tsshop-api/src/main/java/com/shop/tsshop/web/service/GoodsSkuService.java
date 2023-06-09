package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.GoodsSku;

/**
 * <p>
 * 单品SKU表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
public interface GoodsSkuService extends IService<GoodsSku> {
    /**
     * 更新库存
     * @param num           num
     * @param id            id
     * @return              统一返回
     */
    int updateStock(int num,Long id);
}
