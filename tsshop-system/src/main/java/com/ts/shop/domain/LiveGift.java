package com.ts.shop.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 直播礼物对象 live_gift
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
public class LiveGift extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    private Long id;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Long giftOrderBy;

    /** 礼物名称 */
    @Excel(name = "礼物名称")
    private String giftName;

    /** 礼物封面 */
    @Excel(name = "礼物封面")
    private String front;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /** 礼物费率 */
    @Excel(name = "礼物费率")
    private String giftRate;

    /** 删除标志 */
    @Excel(name = "删除标志")
    private Integer deleted;

    /** 上架状态 0 上架 1 下架 */
    @Excel(name = "上架状态 0 上架 1 下架")
    private Integer shelves;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGiftOrderBy(Long giftOrderBy) 
    {
        this.giftOrderBy = giftOrderBy;
    }

    public Long getGiftOrderBy() 
    {
        return giftOrderBy;
    }
    public void setGiftName(String giftName) 
    {
        this.giftName = giftName;
    }

    public String getGiftName() 
    {
        return giftName;
    }
    public void setFront(String front) 
    {
        this.front = front;
    }

    public String getFront() 
    {
        return front;
    }
    public void setUnitPrice(BigDecimal unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() 
    {
        return unitPrice;
    }
    public void setGiftRate(String giftRate) 
    {
        this.giftRate = giftRate;
    }

    public String getGiftRate() 
    {
        return giftRate;
    }
    public void setDeleted(Integer deleted) 
    {
        this.deleted = deleted;
    }

    public Integer getDeleted() 
    {
        return deleted;
    }
    public void setShelves(Integer shelves) 
    {
        this.shelves = shelves;
    }

    public Integer getShelves() 
    {
        return shelves;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("giftOrderBy", getGiftOrderBy())
            .append("giftName", getGiftName())
            .append("front", getFront())
            .append("unitPrice", getUnitPrice())
            .append("giftRate", getGiftRate())
            .append("deleted", getDeleted())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("shelves", getShelves())
            .toString();
    }
}
