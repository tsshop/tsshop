package com.shop.tsshop.web.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderMoneyVo {
    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单价格
     */
    private BigDecimal amount;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 销售属性组合字符串 格式是p1:v1;p2:v2
     */
    //"属性组合
    private String properties;
}
