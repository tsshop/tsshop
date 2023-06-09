package com.ts.shop.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 退款对象 return_order
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class ReturnOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;

    /** 真实退款金额 */
    @Excel(name = "真实退款金额")
    private BigDecimal reallyAmt;

    /** 订单退款金额 */
    @Excel(name = "订单退款金额")
    private BigDecimal orderAmt;

    /** 申请退款金额 */
    @Excel(name = "申请退款金额")
    private BigDecimal returnAmt;

    /** 申请退款理由 */
    @Excel(name = "申请退款理由")
    private String returnReason;

    /** 申请退款图片 */
    @Excel(name = "申请退款图片")
    private String returnImg;

    /** 申请退款类型 1：仅退款（没收到货）2：仅退款（收到货了）3：退货退款 */
    @Excel(name = "申请退款类型 1：仅退款", readConverterExp = "没=收到货")
    private Integer returnType;

    /** 申请退款文字描述 */
    @Excel(name = "申请退款文字描述")
    private String returnDetail;

    /** 退货地址 */
    @Excel(name = "退货地址")
    private Long addressId;

    /** 1:发起退货（等待审核） 2：审核拒绝 3：退款成功 4 等待填物流单号 5 物流待审核 */
    @Excel(name = "1:发起退货", readConverterExp = "等=待审核")
    private Integer status;

    /** 物流名字 */
    @Excel(name = "物流名字")
    private String expressName;

    /** 物流单号 */
    @Excel(name = "物流单号")
    private String expressNo;

    /** 物流编码 */
    @Excel(name = "物流编码")
    private String express;

    /** 拒绝理由 */
    @Excel(name = "拒绝理由")
    private String refuseDetail;

    /** 退单号 */
    @Excel(name = "退单号")
    private String outRefundNo;

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
    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }
    public void setReallyAmt(BigDecimal reallyAmt) 
    {
        this.reallyAmt = reallyAmt;
    }

    public BigDecimal getReallyAmt() 
    {
        return reallyAmt;
    }
    public void setOrderAmt(BigDecimal orderAmt) 
    {
        this.orderAmt = orderAmt;
    }

    public BigDecimal getOrderAmt() 
    {
        return orderAmt;
    }
    public void setReturnAmt(BigDecimal returnAmt) 
    {
        this.returnAmt = returnAmt;
    }

    public BigDecimal getReturnAmt() 
    {
        return returnAmt;
    }
    public void setReturnReason(String returnReason) 
    {
        this.returnReason = returnReason;
    }

    public String getReturnReason() 
    {
        return returnReason;
    }
    public void setReturnImg(String returnImg) 
    {
        this.returnImg = returnImg;
    }

    public String getReturnImg() 
    {
        return returnImg;
    }
    public void setReturnType(Integer returnType) 
    {
        this.returnType = returnType;
    }

    public Integer getReturnType() 
    {
        return returnType;
    }
    public void setReturnDetail(String returnDetail) 
    {
        this.returnDetail = returnDetail;
    }

    public String getReturnDetail() 
    {
        return returnDetail;
    }
    public void setAddressId(Long addressId) 
    {
        this.addressId = addressId;
    }

    public Long getAddressId() 
    {
        return addressId;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setExpressName(String expressName) 
    {
        this.expressName = expressName;
    }

    public String getExpressName() 
    {
        return expressName;
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
    public void setRefuseDetail(String refuseDetail) 
    {
        this.refuseDetail = refuseDetail;
    }

    public String getRefuseDetail() 
    {
        return refuseDetail;
    }
    public void setOutRefundNo(String outRefundNo) 
    {
        this.outRefundNo = outRefundNo;
    }

    public String getOutRefundNo() 
    {
        return outRefundNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("orderNo", getOrderNo())
            .append("reallyAmt", getReallyAmt())
            .append("orderAmt", getOrderAmt())
            .append("returnAmt", getReturnAmt())
            .append("returnReason", getReturnReason())
            .append("returnImg", getReturnImg())
            .append("returnType", getReturnType())
            .append("returnDetail", getReturnDetail())
            .append("addressId", getAddressId())
            .append("status", getStatus())
            .append("expressName", getExpressName())
            .append("expressNo", getExpressNo())
            .append("express", getExpress())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("refuseDetail", getRefuseDetail())
            .append("outRefundNo", getOutRefundNo())
            .toString();
    }
}
