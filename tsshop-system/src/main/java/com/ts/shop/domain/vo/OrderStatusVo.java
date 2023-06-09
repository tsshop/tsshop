package com.ts.shop.domain.vo;

import lombok.Data;

/**
 * @ClassName OrderStatusVo
 * @Author TS SHOP
 * @create 2023/6/7
 */
@Data
public class OrderStatusVo {
    /**
     * 已完成订单数
     */
    private Long finishOrderNum;
    /**
     * 待发货订单
     */
    private Long waitDeliverOrderNum;
    /**
     * 待支付订单
     */
    private Long waitPayOrderNum;
    /**
     * 待收货订单
     */
    private Long waitReceivingOrderNum;
    /**
     * 已取消订单
     */
    private Long cancelOrderNum;
    /**
     * 订单数
     */
    private Long orderNum;
}
