package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 店铺对象 store
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class Store extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 店铺名字 */
    @Excel(name = "店铺名字")
    private String name;

    /** 图 */
    @Excel(name = "图")
    private String banner;

    /** 头像 */
    @Excel(name = "头像")
    private String avatar;

    /** 审核状态0审核中 1上架 2下架 */
    @Excel(name = "审核状态0审核中 1上架 2下架")
    private Integer shelves;

    /** 是否已删除 0-未删除 1-已删除 */
    @Excel(name = "是否已删除 0-未删除 1-已删除")
    private Integer deleted;

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
    public void setBanner(String banner) 
    {
        this.banner = banner;
    }

    public String getBanner() 
    {
        return banner;
    }
    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("banner", getBanner())
            .append("avatar", getAvatar())
            .append("shelves", getShelves())
            .append("deleted", getDeleted())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
