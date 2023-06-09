package com.ts.shop.domain;

import java.math.BigDecimal;

import lombok.Data;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 秒杀活动商品对象 seckill_goods
 * 
 * @author xu
 * @date 2023-02-21
 */
@Data
public class SeckillGoods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private Long id;

    /** 商品id */
    @Excel(name = "商品id")
    private Long goodsId;

    /** skuid */
    @Excel(name = "skuid")
    private Long skuId;

    /** 特价 */
    @Excel(name = "特价")
    private BigDecimal offer;

    /** 秒杀活动id */
    @Excel(name = "秒杀活动id")
    private Long seckillId;

    /** 时间点 */
    private Integer time;

    private Integer stock;
    private Integer quantity;

}
