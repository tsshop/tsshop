package com.ts.shop.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 单品SKU对象 goods_sku
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class GoodsSku extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 单品ID */
    private Long skuId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long goodsId;

    /** 销售属性组合字符串 格式是p1:v1;p2:v2 */
    @Excel(name = "销售属性组合字符串 格式是p1:v1;p2:v2")
    private String properties;

    /** 原价 */
    @Excel(name = "原价")
    private BigDecimal oriPrice;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 实际库存 */
    @Excel(name = "实际库存")
    private Long stocks;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long actualStocks;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recTime;

    /** 商家编码 */
    @Excel(name = "商家编码")
    private String partyCode;

    /** sku图片 */
    @Excel(name = "sku图片")
    private String pic;

    /** sku名称 */
    @Excel(name = "sku名称")
    private String skuName;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String prodName;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long version;

    /** 1 禁用 0 启用 */
    @Excel(name = "1 禁用 0 启用")
    private Integer status;

    /** 0 正常 1 已被删除 */
    @Excel(name = "0 正常 1 已被删除")
    private Integer deleted;

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    private BigDecimal costPrice;

    public void setSkuId(Long skuId) 
    {
        this.skuId = skuId;
    }

    public Long getSkuId() 
    {
        return skuId;
    }
    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }
    public void setProperties(String properties) 
    {
        this.properties = properties;
    }

    public String getProperties() 
    {
        return properties;
    }
    public void setOriPrice(BigDecimal oriPrice) 
    {
        this.oriPrice = oriPrice;
    }

    public BigDecimal getOriPrice() 
    {
        return oriPrice;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setStocks(Long stocks) 
    {
        this.stocks = stocks;
    }

    public Long getStocks() 
    {
        return stocks;
    }
    public void setActualStocks(Long actualStocks) 
    {
        this.actualStocks = actualStocks;
    }

    public Long getActualStocks() 
    {
        return actualStocks;
    }
    public void setRecTime(Date recTime) 
    {
        this.recTime = recTime;
    }

    public Date getRecTime() 
    {
        return recTime;
    }
    public void setPartyCode(String partyCode) 
    {
        this.partyCode = partyCode;
    }

    public String getPartyCode() 
    {
        return partyCode;
    }
    public void setPic(String pic) 
    {
        this.pic = pic;
    }

    public String getPic() 
    {
        return pic;
    }
    public void setSkuName(String skuName) 
    {
        this.skuName = skuName;
    }

    public String getSkuName() 
    {
        return skuName;
    }
    public void setProdName(String prodName) 
    {
        this.prodName = prodName;
    }

    public String getProdName() 
    {
        return prodName;
    }
    public void setVersion(Long version) 
    {
        this.version = version;
    }

    public Long getVersion() 
    {
        return version;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
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
            .append("skuId", getSkuId())
            .append("goodsId", getGoodsId())
            .append("properties", getProperties())
            .append("oriPrice", getOriPrice())
            .append("price", getPrice())
            .append("stocks", getStocks())
            .append("actualStocks", getActualStocks())
            .append("updateTime", getUpdateTime())
            .append("recTime", getRecTime())
            .append("partyCode", getPartyCode())
            .append("pic", getPic())
            .append("skuName", getSkuName())
            .append("prodName", getProdName())
            .append("version", getVersion())
            .append("status", getStatus())
            .append("deleted", getDeleted())
            .toString();
    }
}
