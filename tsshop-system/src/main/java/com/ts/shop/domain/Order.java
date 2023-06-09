package com.ts.shop.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 订单对象 order
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@Data
public class Order extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNo;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 单品ID */
    @Excel(name = "单品ID")
    private Long skuId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long goodsId;

    /** 收货地址ID */
    @Excel(name = "收货地址ID")
    private Long addressId;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    /** 订单价格 */
    @Excel(name = "订单价格")
    private BigDecimal amount;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 购买数量 */
    @Excel(name = "购买数量")
    private Long quantity;

    /** 运费 */
    @Excel(name = "运费")
    private BigDecimal freight;


    private String goodsName;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /** (0, "待付款"),(1, "待发货"),(2, "待收货"),(3, "已完成"),(4, "待收货"),(-1, "已取消"); */
    @Excel(name = "(0, 待付款),(1, 待发货),(2, 待收货),(3, 已完成),(4, 待收货),(-1, 已取消);")
    private Integer orderStatus;

    /** 下单时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下单时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /** 收货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendTime;

    /** 支付交易号 */
    @Excel(name = "支付交易号")
    private String payNo;

    /** 物流单号 */
    @Excel(name = "物流单号")
    private String expressNo;

    /** 物流编码 */
    @Excel(name = "物流编码")
    private String express;

    /** 支付商家订单编号 */
    @Excel(name = "支付商家订单编号")
    private String orderNoPay;

    /** 退款单号 */
    @Excel(name = "退款单号")
    private Long returnId;

    /** ip地址 */
    @Excel(name = "ip地址")
    private String ip;

    /** 是否已删除 （0：正常/1：删除） */
    @Excel(name = "是否已删除 ", readConverterExp = "0=：正常/1：删除")
    private Integer deleted;

    /**
     * 支付类型
     */
    private String payType;
    /**
     * 支付通道ID
     */
    private Long payThoroughfareId;


    /**
     * 直播小店 ID
     */
    private Long liveStoreId;

    /**
     * 支付记录 ID
     */
    private Long payLogId;

    /**
     * 订单类型 0 商品购买 1 礼物购买
     */
    private Integer orderType;

    public Long getLiveStoreId() {
        return liveStoreId;
    }

    public void setLiveStoreId(Long liveStoreId) {
        this.liveStoreId = liveStoreId;
    }

    public Long getPayLogId() {
        return payLogId;
    }

    public void setPayLogId(Long payLogId) {
        this.payLogId = payLogId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Long getPayThoroughfareId() {
        return payThoroughfareId;
    }

    public void setPayThoroughfareId(Long payThoroughfareId) {
        this.payThoroughfareId = payThoroughfareId;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo()
    {
        return orderNo;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
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
    public void setAddressId(Long addressId)
    {
        this.addressId = addressId;
    }

    public Long getAddressId()
    {
        return addressId;
    }
    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setQuantity(Long quantity)
    {
        this.quantity = quantity;
    }

    public Long getQuantity()
    {
        return quantity;
    }
    public void setFreight(BigDecimal freight)
    {
        this.freight = freight;
    }

    public BigDecimal getFreight()
    {
        return freight;
    }
    public void setOrderStatus(Integer orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public Integer getOrderStatus()
    {
        return orderStatus;
    }
    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }

    public Date getPayTime()
    {
        return payTime;
    }
    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }

    public Date getSendTime()
    {
        return sendTime;
    }
    public void setPayNo(String payNo)
    {
        this.payNo = payNo;
    }

    public String getPayNo()
    {
        return payNo;
    }
    public void setExpressNo(String expressNo)
    {
        this.expressNo = expressNo;
    }

    public String getExpressNo()
    {
        return expressNo;
    }
    public void setExpress(String express)
    {
        this.express = express;
    }

    public String getExpress()
    {
        return express;
    }
    public void setOrderNoPay(String orderNoPay)
    {
        this.orderNoPay = orderNoPay;
    }

    public String getOrderNoPay()
    {
        return orderNoPay;
    }
    public void setReturnId(Long returnId)
    {
        this.returnId = returnId;
    }

    public Long getReturnId()
    {
        return returnId;
    }
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getIp()
    {
        return ip;
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
            .append("orderNo", getOrderNo())
            .append("userId", getUserId())
            .append("skuId", getSkuId())
            .append("goodsId", getGoodsId())
            .append("addressId", getAddressId())
            .append("totalAmount", getTotalAmount())
            .append("amount", getAmount())
            .append("price", getPrice())
            .append("quantity", getQuantity())
            .append("freight", getFreight())
            .append("orderStatus", getOrderStatus())
            .append("payTime", getPayTime())
            .append("sendTime", getSendTime())
            .append("payNo", getPayNo())
            .append("expressNo", getExpressNo())
            .append("express", getExpress())
            .append("orderNoPay", getOrderNoPay())
            .append("returnId", getReturnId())
            .append("remark", getRemark())
            .append("ip", getIp())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("deleted", getDeleted())
            .toString();
    }
}
