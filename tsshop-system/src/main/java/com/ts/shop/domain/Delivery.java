package com.ts.shop.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 物流公司对象 delivery
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class Delivery extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "ID")
    private Long id;

    /** 物流公司名称 */
    @Excel(name = "物流公司名称")
    private String dvyName;

    /** 公司主页 */
    @Excel(name = "公司主页")
    private String companyHomeUrl;

    /** 建立时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "建立时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recTime;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    /** 物流查询接口 */
    @Excel(name = "物流查询接口")
    private String queryUrl;

    /** 快递鸟编码 */
    @Excel(name = "快递鸟编码")
    private String expressBird;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDvyName(String dvyName) 
    {
        this.dvyName = dvyName;
    }

    public String getDvyName() 
    {
        return dvyName;
    }
    public void setCompanyHomeUrl(String companyHomeUrl) 
    {
        this.companyHomeUrl = companyHomeUrl;
    }

    public String getCompanyHomeUrl() 
    {
        return companyHomeUrl;
    }
    public void setRecTime(Date recTime) 
    {
        this.recTime = recTime;
    }

    public Date getRecTime() 
    {
        return recTime;
    }
    public void setModifyTime(Date modifyTime) 
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() 
    {
        return modifyTime;
    }
    public void setQueryUrl(String queryUrl) 
    {
        this.queryUrl = queryUrl;
    }

    public String getQueryUrl() 
    {
        return queryUrl;
    }
    public void setExpressBird(String expressBird) 
    {
        this.expressBird = expressBird;
    }

    public String getExpressBird() 
    {
        return expressBird;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dvyName", getDvyName())
            .append("companyHomeUrl", getCompanyHomeUrl())
            .append("recTime", getRecTime())
            .append("modifyTime", getModifyTime())
            .append("queryUrl", getQueryUrl())
            .append("expressBird", getExpressBird())
            .toString();
    }
}
