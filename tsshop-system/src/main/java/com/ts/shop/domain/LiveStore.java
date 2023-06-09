package com.ts.shop.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 直播小店对象 live_store
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
@Data
public class LiveStore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 直播小店id */
    @Excel(name = "直播小店id")
    private Long id;

    /** 用戶id */
    @Excel(name = "用戶id")
    private Long userId;

    /** 店铺名称 */
    @Excel(name = "店铺名称")
    private String storeName;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String storePhone;

    /** 店铺logo */
    @Excel(name = "店铺logo")
    private String storeHeadPortrait;

    /** $column.columnComment */
    private BigDecimal amt;

    /** 删除标记 0 未删除 1 已删除 */
    private Integer deleted;

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
    public void setStoreName(String storeName) 
    {
        this.storeName = storeName;
    }

    public String getStoreName() 
    {
        return storeName;
    }
    public void setStorePhone(String storePhone) 
    {
        this.storePhone = storePhone;
    }

    public String getStorePhone() 
    {
        return storePhone;
    }
    public void setStoreHeadPortrait(String storeHeadPortrait) 
    {
        this.storeHeadPortrait = storeHeadPortrait;
    }

    public String getStoreHeadPortrait() 
    {
        return storeHeadPortrait;
    }
    public void setAmt(BigDecimal amt) 
    {
        this.amt = amt;
    }

    public BigDecimal getAmt() 
    {
        return amt;
    }
    public void setDeleted(Integer deleted) 
    {
        this.deleted = deleted;
    }

    public Integer getDeleted() 
    {
        return deleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("storeName", getStoreName())
            .append("storePhone", getStorePhone())
            .append("storeHeadPortrait", getStoreHeadPortrait())
            .append("amt", getAmt())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
