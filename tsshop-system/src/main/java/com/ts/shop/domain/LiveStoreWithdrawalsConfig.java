package com.ts.shop.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 店铺提现配置对象 live_store_withdrawals_config
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
public class LiveStoreWithdrawalsConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    private Long id;

    /** 提现方式 */
    @Excel(name = "提现方式")
    private String withdrawType;

    /** 最小提现金额 */
    @Excel(name = "最小提现金额")
    private BigDecimal minWithdrawAmt;

    /** 最小提现金额 */
    @Excel(name = "最小提现金额")
    private BigDecimal maxWithdrawAmt;

    /** 允许提现 0 允许  1 不允许 */
    @Excel(name = "允许提现 0 允许  1 不允许")
    private Boolean allowableWithdrawal;

    /** 允许小数 0 允许  1 不允许 */
    @Excel(name = "允许小数 0 允许  1 不允许")
    private Boolean allowableDecimal;

    /** 提现周期 0 每天  1 每周  2 每月 */
    @Excel(name = "提现周期 0 每天  1 每周  2 每月")
    private Integer withdrawPeriod;

    /** 提现次数 */
    @Excel(name = "提现次数")
    private Long withdrawNum;

    /** 提现费率 */
    @Excel(name = "提现费率")
    private String withdrawRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(String withdrawType) {
        this.withdrawType = withdrawType;
    }

    public BigDecimal getMinWithdrawAmt() {
        return minWithdrawAmt;
    }

    public void setMinWithdrawAmt(BigDecimal minWithdrawAmt) {
        this.minWithdrawAmt = minWithdrawAmt;
    }

    public BigDecimal getMaxWithdrawAmt() {
        return maxWithdrawAmt;
    }

    public void setMaxWithdrawAmt(BigDecimal maxWithdrawAmt) {
        this.maxWithdrawAmt = maxWithdrawAmt;
    }

    public Boolean getAllowableWithdrawal() {
        return allowableWithdrawal;
    }

    public void setAllowableWithdrawal(Boolean allowableWithdrawal) {
        this.allowableWithdrawal = allowableWithdrawal;
    }

    public Boolean getAllowableDecimal() {
        return allowableDecimal;
    }

    public void setAllowableDecimal(Boolean allowableDecimal) {
        this.allowableDecimal = allowableDecimal;
    }

    public Integer getWithdrawPeriod() {
        return withdrawPeriod;
    }

    public void setWithdrawPeriod(Integer withdrawPeriod) {
        this.withdrawPeriod = withdrawPeriod;
    }

    public Long getWithdrawNum() {
        return withdrawNum;
    }

    public void setWithdrawNum(Long withdrawNum) {
        this.withdrawNum = withdrawNum;
    }

    public String getWithdrawRate() {
        return withdrawRate;
    }

    public void setWithdrawRate(String withdrawRate) {
        this.withdrawRate = withdrawRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("withdrawType", getWithdrawType())
            .append("minWithdrawAmt", getMinWithdrawAmt())
            .append("maxWithdrawAmt", getMaxWithdrawAmt())
            .append("allowableWithdrawal", getAllowableWithdrawal())
            .append("allowableDecimal", getAllowableDecimal())
            .append("withdrawPeriod", getWithdrawPeriod())
            .append("withdrawNum", getWithdrawNum())
            .append("withdrawRate", getWithdrawRate())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
