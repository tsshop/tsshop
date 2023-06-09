package com.shop.tsshop.web.model.vo;

import lombok.Data;

/**
 * @ClassName AmountType
 * @Author TS SHOP
 * @create 2023/6/1
 */
@Data
public class AmountType {
    /**
     * 支付通道 ID
     */
    private Long payThoroughfareId;
    /**
     * 支付类型
     */
    private String type;
    /**
     * 支付LOGO
     */
    private String logo;
    /**
     * 支付类型名
     */
    private String name;
}
