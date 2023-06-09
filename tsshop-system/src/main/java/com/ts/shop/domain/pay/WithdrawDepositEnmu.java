package com.ts.shop.domain.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName WithdrawDepositEnmu
 * @Author TS SHOP
 * @create 2023/5/31
 */
@Getter
@AllArgsConstructor
public enum WithdrawDepositEnmu {
    ALI_WITHDRAW_DEPOSIT("ALI_WITHDRAW_DEPOSIT","支付宝");
    private String code;
    private String msg;
}
