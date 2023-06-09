package com.shop.tsshop.web.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName LiveStoreDelGoodsDto
 * @Author TS SHOP
 * @create 2023/5/25
 */
@Data
public class LiveStoreDelGoodsDto {
    /**
     * 直播小店 ID
     */
    private Long liveStoreId;
    /**
     * 商品 ID
     */
    private List<Long> goodsIds;
}
