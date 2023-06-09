package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 商品类型对象 goods_type
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class GoodsType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商品类型ID */
    @Excel(name = "商品类型ID")
    private Long id;

    /** 商品类型图标 */
    @Excel(name = "商品类型图标")
    private String avatar;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String banner;

    /** 商品类别名称 */
    @Excel(name = "商品类别名称")
    private String name;

    /** 是否已删除 （1：正常/-1：删除） */
    @Excel(name = "是否已删除 ", readConverterExp = "1=：正常/-1：删除")
    private Integer deleted;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 父分类id，0为一级 */
    @Excel(name = "父分类id，0为一级")
    private Long pTypeId;

    /** 级别 */
    @Excel(name = "级别")
    private Long level;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
    }
    public void setBanner(String banner) 
    {
        this.banner = banner;
    }

    public String getBanner() 
    {
        return banner;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
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
    public void setpTypeId(Long pTypeId) 
    {
        this.pTypeId = pTypeId;
    }

    public Long getpTypeId() 
    {
        return pTypeId;
    }
    public void setLevel(Long level) 
    {
        this.level = level;
    }

    public Long getLevel() 
    {
        return level;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("avatar", getAvatar())
            .append("banner", getBanner())
            .append("name", getName())
            .append("deleted", getDeleted())
            .append("sort", getSort())
            .append("pTypeId", getpTypeId())
            .append("level", getLevel())
            .toString();
    }
}
