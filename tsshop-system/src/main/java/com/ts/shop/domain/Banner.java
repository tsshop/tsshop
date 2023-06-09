package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 用户首页轮播图片对象 banner
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class Banner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 轮播图id */
    @Excel(name = "轮播图id")
    private Long id;

    /** 图片url */
    @Excel(name = "图片url")
    private String imgUrl;

    /** 跳转地址 */
    @Excel(name = "跳转地址")
    private String linkedUrl;

    /** 是否删除 0-未删除 1-已删除 */
    @Excel(name = "是否删除 0-未删除 1-已删除")
    private Integer deleted;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setImgUrl(String imgUrl) 
    {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() 
    {
        return imgUrl;
    }
    public void setLinkedUrl(String linkedUrl) 
    {
        this.linkedUrl = linkedUrl;
    }

    public String getLinkedUrl() 
    {
        return linkedUrl;
    }
    public void setDeleted(Integer deleted) 
    {
        this.deleted = deleted;
    }

    public Integer getDeleted() 
    {
        return deleted;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("imgUrl", getImgUrl())
            .append("linkedUrl", getLinkedUrl())
            .append("deleted", getDeleted())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("sort", getSort())
            .toString();
    }
}
