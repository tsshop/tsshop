package com.ts.shop.domain.vo;

import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 秒杀活动商品对象 seckill_goods
 * 
 * @author xu
 * @date 2023-02-21
 */
@Data
public class SeckillGoodsVo
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private Long id;

    /** 商品id */
    private Long goodsId;

    /** skuid */
    private Long skuId;

    /** 特价 */
    private BigDecimal offer;

    /** 秒杀活动id */
    private Long seckillId;

    /** 时间点 */
    private Integer time;

    private Integer stock;
    private Integer quantity;

    private BigDecimal price;
    private String pic;
    /** sku名称 */
    private String skuName;
    /** 商品名称 */
    private String prodName;
}
