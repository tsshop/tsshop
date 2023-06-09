package com.shop.tsshop.web.model.vo;

import com.shop.tsshop.web.model.domain.Goods;
import com.shop.tsshop.web.model.domain.LiveStoreGoodsSku;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName LiveStoreGoodsVo
 * @Author TS SHOP
 * @create 2023/5/25
 */
@Data
public class LiveStoreGoodsVo {
    /**
     * 商品ID
     */
    private Long id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品主图
     */
    private String headPortrait;
    /**
     * 划线价格
     */
    private BigDecimal scribingPrice;
    /**
     * 售价
     */
    private BigDecimal price;

    private List<LiveStoreGoodsSku> liveStoreGoodsSkus;
}
