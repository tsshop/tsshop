package com.shop.tsshop.web.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName LiveGiftBuyRecordVo
 * @Author TS SHOP
 * @create 2023/5/29
 */
@Data
public class LiveGiftBuyRecordVo {
    /**
     * 订单 ID
     */
    private Long orderId;

    /**
     * 礼物名称
     */
    private String giftName;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 购买时间
     */
    private String buyTime;

    /**
     * 金额
     */
    private BigDecimal totalAmount;

    /**
     * 购买数量
     */
    private Integer quantity;
}
