package com.shop.tsshop.web.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName OrderPayDto
 * @Author TS SHOP
 * @create 2023/5/19
 */
@Data
public class OrderPayDto {
    /**
     * 订单ID集合
     */
    private List<Long> orderId;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 支付通道 ID
     */
    private Long payThoroughfareId;
    /**
     * openId
     */
    private String code;
}
