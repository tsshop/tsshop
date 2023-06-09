package com.ts.shop.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName SalesRankingVo
 * @Author TS SHOP
 * @create 2023/6/7
 */
@Data
public class SalesRankingVo {
    /**
     * 商品 ID
     */
    private Long id;
    /**
     * 商品主图
     */
    private String img;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品销量
     */
    private Long saleNum;
    /**
     * 成交金额
     */
    private BigDecimal saleMoney;
    /**
     * 排名
     */
    private Integer sort;

}
