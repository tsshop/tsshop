package com.ts.shop.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 版本对象 edition
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class Edition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long id;

    /** 版本编号 */
    @Excel(name = "版本编号")
    private String verionNo;

    /** 平台 1/安卓、2/ios */
    @Excel(name = "平台 1/安卓、2/ios")
    private Integer type;

    /** 下载链接 */
    @Excel(name = "下载链接")
    private String url;

    /** 发布状态，1/已发布， 2待发布， 3已下架 */
    @Excel(name = "发布状态，1/已发布， 2待发布， 3已下架")
    private Integer status;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date releaseTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setVerionNo(String verionNo) 
    {
        this.verionNo = verionNo;
    }

    public String getVerionNo() 
    {
        return verionNo;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setReleaseTime(Date releaseTime) 
    {
        this.releaseTime = releaseTime;
    }

    public Date getReleaseTime() 
    {
        return releaseTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("verionNo", getVerionNo())
            .append("type", getType())
            .append("url", getUrl())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("status", getStatus())
            .append("releaseTime", getReleaseTime())
            .toString();
    }
}
