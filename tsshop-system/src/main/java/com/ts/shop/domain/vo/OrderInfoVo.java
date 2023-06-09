package com.ts.shop.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName OrderInfoVo
 * @Author TS SHOP
 * @create 2023/6/7
 */
@Data
public class OrderInfoVo {
    /**
     * 已完成订单数
     */
    private Long finishOrderNum;

    /**
     * 累计金额
     */
    private BigDecimal orderMoney;

    /**
     * 已完成订单数
     */
    private Long returnOrderNum;

    /**
     * 累计退款金额
     */
    private BigDecimal returnOrderMoney;

}
