package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.SeckillGoods;
import com.shop.tsshop.web.model.vo.SeckillGoodsVo;

import java.util.List;

/**
 * <p>
 * 秒杀活动商品 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-21
 */
public interface SeckillGoodsService extends IService<SeckillGoods> {

    /**
     * 获取秒杀商品信息
     * @param seckillId              id
     * @param time                   时间戳
     * @return                       秒杀商品信息
     */
    List<SeckillGoodsVo> getGoodsVo(Long seckillId, int time);

    /**
     * 获取秒杀商品信息
     * @param skuId                  sku
     * @param k                      k
     * @return                       秒杀商品
     */
    SeckillGoods getGoodsById(Long skuId, int k);

    /**
     * 更新库存
     * @param num                    数量
     * @param id                     id
     * @return                       结果
     */
    int updateStock(int num, Long id);

    /**
     * 获取秒杀商品
     * @param id                      id
     * @return                        秒杀商品集合
     */
    List<SeckillGoods> getGoodsListById(Long id);
}
