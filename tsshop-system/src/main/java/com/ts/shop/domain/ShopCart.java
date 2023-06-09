package com.ts.shop.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 购物车对象 shop_cart
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class ShopCart extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long goodsId;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal amt;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer status;

    /** 购买数量 */
    @Excel(name = "购买数量")
    private Long purchaseQuantity;

    /** 单品ID */
    @Excel(name = "单品ID")
    private Long skuId;

    /** 是否已删除 （1：正常/-1：删除） */
    @Excel(name = "是否已删除 ", readConverterExp = "1=：正常/-1：删除")
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
    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }
    public void setAmt(BigDecimal amt) 
    {
        this.amt = amt;
    }

    public BigDecimal getAmt() 
    {
        return amt;
    }
    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setPurchaseQuantity(Long purchaseQuantity) 
    {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Long getPurchaseQuantity() 
    {
        return purchaseQuantity;
    }
    public void setSkuId(Long skuId) 
    {
        this.skuId = skuId;
    }

    public Long getSkuId() 
    {
        return skuId;
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
            .append("goodsId", getGoodsId())
            .append("amt", getAmt())
            .append("totalAmount", getTotalAmount())
            .append("status", getStatus())
            .append("purchaseQuantity", getPurchaseQuantity())
            .append("skuId", getSkuId())
            .append("updateTime", getUpdateTime())
            .append("createTime", getCreateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
