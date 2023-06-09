package com.ts.shop.domain.param;

import com.ts.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName CashOutConfigParams
 * @Author TS SHOP
 * @create 2023/6/9
 */
@Data
public class CashOutConfigParams {
    /**
     * 最小提现金额
     */
    private BigDecimal minWithdrawAmt;
    /**
     * 最大提现金额
     */
    private BigDecimal maxWithdrawAmt;

    /** 允许提现 0 允许  1 不允许 */
    private Boolean allowableWithdrawal;

    /** 允许小数 0 允许  1 不允许 */
    private Boolean allowableDecimal;

    /** 提现周期 0 每天  1 每周  2 每月 */
    private Integer withdrawPeriod;

    /** 提现次数 */
    private Long withdrawNum;

    /** 提现费率 */
    private String withdrawRate;
}
