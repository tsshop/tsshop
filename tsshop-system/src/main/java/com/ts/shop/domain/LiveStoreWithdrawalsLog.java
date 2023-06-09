package com.ts.shop.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 店铺提现记录对象 live_store_withdrawals_log
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
public class LiveStoreWithdrawalsLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    private Long id;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long liveStoreId;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal amt;

    /** 状态 0 发起 1 到账 2 失败 */
    @Excel(name = "状态 0 发起 1 到账 2 失败")
    private Integer status;

    /** 提现方式 */
    @Excel(name = "提现方式")
    private String withdrawalsType;

    public String getWithdrawalsType() {
        return withdrawalsType;
    }

    public void setWithdrawalsType(String withdrawalsType) {
        this.withdrawalsType = withdrawalsType;
    }

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String realName;

    /** 收款账户 */
    @Excel(name = "收款账户")
    private String account;

    /** 编号 */
    @Excel(name = "编号")
    private String serialNumber;

    private String failReason;

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLiveStoreId(Long liveStoreId) 
    {
        this.liveStoreId = liveStoreId;
    }

    public Long getLiveStoreId() 
    {
        return liveStoreId;
    }
    public void setAmt(BigDecimal amt) 
    {
        this.amt = amt;
    }

    public BigDecimal getAmt() 
    {
        return amt;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }
    public void setAccount(String account) 
    {
        this.account = account;
    }

    public String getAccount() 
    {
        return account;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("liveStoreId", getLiveStoreId())
            .append("amt", getAmt())
            .append("status", getStatus())
            .append("withdrawalsType", getWithdrawalsType())
            .append("realName", getRealName())
            .append("account", getAccount())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("serialNumber", getSerialNumber())
            .toString();
    }
}
