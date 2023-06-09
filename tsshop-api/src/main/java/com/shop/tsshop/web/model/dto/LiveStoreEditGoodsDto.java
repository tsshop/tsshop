package com.shop.tsshop.web.model.dto;

import com.shop.tsshop.web.model.domain.LiveStoreGoodsSku;
import lombok.Data;

import java.util.List;

/**
 * @ClassName LiveStoreEditGoodsDto
 * @Author TS SHOP
 * @create 2023/5/24
 */
@Data
public class LiveStoreEditGoodsDto {
    /**
     * 直播小店 ID
     */
    private Long liveStoreId;
    /**
     * SPU ID
     */
    private Long goodsId;
    /**
     * 排序信息
     */
    private Integer orderBy;
    /**
     * SKU 信息
     */
    private List<LiveStoreGoodsSku> liveStoreGoodsSkus;
}
