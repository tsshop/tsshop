package com.shop.tsshop.web.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName LiveStoreCashOutDto
 * @Author TS SHOP
 * @create 2023/6/1
 */
@Data
public class LiveStoreCashOutDto {
    /**
     * 通道 ID
     */
    private Long payThoroughfareId;

    /**
     * 提现类型
     */
    private String payType;

    /**
     * 提现金额
     */
    private BigDecimal cashWithdrawalAmount;

    /**
     * 提现账户 ID
     */
    private Long withdrawalsAccountId;

}
