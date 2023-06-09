package com.ts.shop.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName TurnoverStatistics
 * @Author TS SHOP
 * @create 2023/6/7
 */
@Data
public class TurnoverStatistics {
    private Integer unitNum;
    private BigDecimal count;
}
