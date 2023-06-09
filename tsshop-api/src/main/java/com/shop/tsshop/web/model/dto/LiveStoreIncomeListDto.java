package com.shop.tsshop.web.model.dto;

import lombok.Data;

/**
 * @ClassName liveStoreIncomeListDto
 * @Author TS SHOP
 * @create 2023/5/25
 */
@Data
public class LiveStoreIncomeListDto extends PageDto {
    /**
     * 收益类型 0 直播收益 1 订单收益
     */
    private Integer incomeType;
}
