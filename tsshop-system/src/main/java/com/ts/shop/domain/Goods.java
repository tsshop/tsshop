package com.ts.shop.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 商品对象 goods
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class Goods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商品id */
    private Long id;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String name;

    /** 售价 */
    @Excel(name = "售价")
    private BigDecimal price;

    /** 审核状态 1下架 2上架 */
    @Excel(name = "审核状态 1下架 2上架")
    private Integer shelves;

    /** 是否已删除 0-未删除 1-已删除 */
    @Excel(name = "是否已删除 0-未删除 1-已删除")
    private Integer deleted;

    /** 商品主图 */
    @Excel(name = "商品主图")
    private String headPortrait;

    /** 商品详情 */
    @Excel(name = "商品详情")
    private String detail;

    /** 销售量 */
    @Excel(name = "销售量")
    private Long purchaseQuantity;

    /** 商品分类主键 */
    @Excel(name = "商品分类主键")
    private Long goodsTypeId;

    /** 货号 */
    @Excel(name = "货号")
    private String csno;

    /** 单位 */
    @Excel(name = "单位")
    private String unitName;

    /** 划线价格 */
    @Excel(name = "划线价格")
    private BigDecimal scribingPrice;

    /** 库存 */
    @Excel(name = "库存")
    private Long stockNumber;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 运费模版id */
    @Excel(name = "运费模版id")
    private Long deliveryTemplateId;

    /** 重量 */
    @Excel(name = "重量")
    private BigDecimal weight;

    /** 体积 */
    @Excel(name = "体积")
    private BigDecimal volume;

    /** 城市id */
    @Excel(name = "城市id")
    private Long areaId;

    /** 店铺改动 */
    @Excel(name = "店铺改动")
    private Long storeId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setShelves(Integer shelves) 
    {
        this.shelves = shelves;
    }

    public Integer getShelves() 
    {
        return shelves;
    }
    public void setDeleted(Integer deleted) 
    {
        this.deleted = deleted;
    }

    public Integer getDeleted() 
    {
        return deleted;
    }
    public void setHeadPortrait(String headPortrait) 
    {
        this.headPortrait = headPortrait;
    }

    public String getHeadPortrait() 
    {
        return headPortrait;
    }
    public void setDetail(String detail) 
    {
        this.detail = detail;
    }

    public String getDetail() 
    {
        return detail;
    }
    public void setPurchaseQuantity(Long purchaseQuantity) 
    {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Long getPurchaseQuantity() 
    {
        return purchaseQuantity;
    }
    public void setGoodsTypeId(Long goodsTypeId) 
    {
        this.goodsTypeId = goodsTypeId;
    }

    public Long getGoodsTypeId() 
    {
        return goodsTypeId;
    }
    public void setCsno(String csno) 
    {
        this.csno = csno;
    }

    public String getCsno() 
    {
        return csno;
    }
    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }
    public void setScribingPrice(BigDecimal scribingPrice) 
    {
        this.scribingPrice = scribingPrice;
    }

    public BigDecimal getScribingPrice() 
    {
        return scribingPrice;
    }
    public void setStockNumber(Long stockNumber) 
    {
        this.stockNumber = stockNumber;
    }

    public Long getStockNumber() 
    {
        return stockNumber;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setDeliveryTemplateId(Long deliveryTemplateId) 
    {
        this.deliveryTemplateId = deliveryTemplateId;
    }

    public Long getDeliveryTemplateId() 
    {
        return deliveryTemplateId;
    }
    public void setWeight(BigDecimal weight) 
    {
        this.weight = weight;
    }

    public BigDecimal getWeight() 
    {
        return weight;
    }
    public void setVolume(BigDecimal volume) 
    {
        this.volume = volume;
    }

    public BigDecimal getVolume() 
    {
        return volume;
    }
    public void setAreaId(Long areaId) 
    {
        this.areaId = areaId;
    }

    public Long getAreaId() 
    {
        return areaId;
    }
    public void setStoreId(Long storeId) 
    {
        this.storeId = storeId;
    }

    public Long getStoreId() 
    {
        return storeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("price", getPrice())
            .append("shelves", getShelves())
            .append("deleted", getDeleted())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("headPortrait", getHeadPortrait())
            .append("detail", getDetail())
            .append("purchaseQuantity", getPurchaseQuantity())
            .append("goodsTypeId", getGoodsTypeId())
            .append("csno", getCsno())
            .append("unitName", getUnitName())
            .append("scribingPrice", getScribingPrice())
            .append("stockNumber", getStockNumber())
            .append("sort", getSort())
            .append("deliveryTemplateId", getDeliveryTemplateId())
            .append("weight", getWeight())
            .append("volume", getVolume())
            .append("areaId", getAreaId())
            .append("storeId", getStoreId())
            .toString();
    }
}
