package com.shop.tsshop.web.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName IncomeCountVo
 * @Author TS SHOP
 * @create 2023/5/26
 */
@Data
public class IncomeCountVo {
    /**
     * 礼物收益
     */
    private BigDecimal giftIncome;
    /**
     * 订单收益
     */
    private BigDecimal orderIncome;

    /**
     * 店铺总收益
     */
    private BigDecimal liveStoreIncome;

}
