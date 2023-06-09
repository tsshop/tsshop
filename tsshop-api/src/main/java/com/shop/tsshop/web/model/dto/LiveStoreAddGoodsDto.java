package com.shop.tsshop.web.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName LiveStoreAddGoodsDto
 * @Author TS SHOP
 * @create 2023/5/24
 */
@Data
public class LiveStoreAddGoodsDto {
    /**
     * 直播小店 ID
     */
    private Long liveStoreId;
    /**
     * 商品 ID
     */
    private List<Long> goodsIds;
}
