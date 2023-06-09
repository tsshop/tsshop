package com.ts.shop.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName CashOutConfigEnum
 * @Author TS SHOP
 * @create 2023/6/6
 */
@Getter
@AllArgsConstructor
public enum CashOutConfigEnmu {
    MIN_WITHDRAW_AMT("MIN_WITHDRAW_AMT","最小提现金额"),
    MAX_WITHDRAW_AMT("MAX_WITHDRAW_AMT","最大提现金额"),
    ALLOWABLE_WITHDRAWAL("ALLOWABLE_WITHDRAWAL","允许提现"),
    ALLOWABLE_DECIMAL("ALLOWABLE_DECIMAL","允许小数"),
    WITHDRAW_PERIOD("WITHDRAW_PERIOD","提现周期"),
    WITHDRAW_NUM("WITHDRAW_NUM","提现次数"),
    WITHDRAW_RATE("WITHDRAW_RATE","提现费率");
    private String code;
    private String msg;
}
