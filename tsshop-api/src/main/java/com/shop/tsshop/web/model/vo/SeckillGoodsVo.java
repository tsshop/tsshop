package com.shop.tsshop.web.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeckillGoodsVo {
    private Long goodsId;
    private Long skuId;

    private String name;
    private String src;
    private BigDecimal price;
    private BigDecimal offer;

    private Integer stock;
    private Integer quantity;
}
