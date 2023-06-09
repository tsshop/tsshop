package com.ts.shop.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 店铺收入记录对象 live_store_income_log
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
public class LiveStoreIncomeLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    private Long id;

    /** 用戶id */
    private Long userId;

    /** 直播小店id */
    @Excel(name = "直播小店id")
    private String liveStoreId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal amt;

    /** 收入类型 0 直播收益 1 订单收益 */
    @Excel(name = "收入类型 0 直播收益 1 订单收益")
    private Integer incomeType;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setLiveStoreId(String liveStoreId) 
    {
        this.liveStoreId = liveStoreId;
    }

    public String getLiveStoreId() 
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
    public void setIncomeType(Integer incomeType) 
    {
        this.incomeType = incomeType;
    }

    public Integer getIncomeType() 
    {
        return incomeType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("liveStoreId", getLiveStoreId())
            .append("amt", getAmt())
            .append("incomeType", getIncomeType())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
