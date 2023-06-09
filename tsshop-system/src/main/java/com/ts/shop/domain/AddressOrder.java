package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 用户收货地址对象 address_order
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class AddressOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 收货地址ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 收货人姓名 */
    @Excel(name = "收货人姓名")
    private String name;

    /** 收货电话 */
    @Excel(name = "收货电话")
    private String phone;

    /** 省ID */
    @Excel(name = "省ID")
    private Long province;

    /** 市ID */
    @Excel(name = "市ID")
    private Long city;

    /** 区ID */
    @Excel(name = "区ID")
    private Long area;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String detailAddr;

    /** 是否已删除 （1：删除/0：正常） */
    @Excel(name = "是否已删除 ", readConverterExp = "1=：删除/0：正常")
    private Integer deleted;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNo;

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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setProvince(Long province) 
    {
        this.province = province;
    }

    public Long getProvince() 
    {
        return province;
    }
    public void setCity(Long city) 
    {
        this.city = city;
    }

    public Long getCity() 
    {
        return city;
    }
    public void setArea(Long area) 
    {
        this.area = area;
    }

    public Long getArea() 
    {
        return area;
    }
    public void setDetailAddr(String detailAddr) 
    {
        this.detailAddr = detailAddr;
    }

    public String getDetailAddr() 
    {
        return detailAddr;
    }
    public void setDeleted(Integer deleted) 
    {
        this.deleted = deleted;
    }

    public Integer getDeleted() 
    {
        return deleted;
    }
    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("name", getName())
            .append("phone", getPhone())
            .append("province", getProvince())
            .append("city", getCity())
            .append("area", getArea())
            .append("detailAddr", getDetailAddr())
            .append("deleted", getDeleted())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("orderNo", getOrderNo())
            .toString();
    }
}
