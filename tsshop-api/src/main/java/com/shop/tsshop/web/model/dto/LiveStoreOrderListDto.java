package com.shop.tsshop.web.model.dto;

import lombok.Data;

/**
 * @ClassName LiveStoreOrderListDto
 * @Author TS SHOP
 * @create 2023/5/25
 */
@Data
public class LiveStoreOrderListDto extends PageDto{
    /**
     * 订单状态
     */
    private Integer orderStatus;
}
