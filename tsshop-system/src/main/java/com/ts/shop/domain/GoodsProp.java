package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 规格对象 goods_prop
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class GoodsProp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 属性id */
    @Excel(name = "属性id")
    private Long id;

    /** 属性名称 */
    @Excel(name = "属性名称")
    private String propName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPropName(String propName) 
    {
        this.propName = propName;
    }

    public String getPropName() 
    {
        return propName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("propName", getPropName())
            .append("createTime", getCreateTime())
            .toString();
    }
}
