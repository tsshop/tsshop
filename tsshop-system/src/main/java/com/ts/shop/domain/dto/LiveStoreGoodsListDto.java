package com.ts.shop.domain.dto;

import lombok.Data;

/**
 * @ClassName LiveStoreGoodsListDto
 * @Author TS SHOP
 * @create 2023/5/29
 */
@Data
public class LiveStoreGoodsListDto extends PageDto{
    /**
     * 直播小店 ID
     */
    private Long liveStoreId;
}
