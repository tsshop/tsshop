package com.shop.tsshop.web.model.vo;

import com.shop.tsshop.web.model.domain.GoodsSku;
import com.shop.tsshop.web.model.domain.LiveStoreGoodsSku;
import lombok.Data;

import java.util.List;

/**
 * @ClassName LiveStoreGoodsInfo
 * @Author TS SHOP
 * @create 2023/5/25
 */
@Data
public class LiveStoreGoodsInfoVo {
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
     * 排序信息
     */
    private Integer orderBy;
    /**
     * 店铺商品的规格信息
     */
    private List<LiveStoreGoodsSku> liveStoreGoodsSkus;
}
