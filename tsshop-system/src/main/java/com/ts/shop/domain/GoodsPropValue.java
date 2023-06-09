package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 规格参数对象 goods_prop_value
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class GoodsPropValue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 属性值ID */
    private Long valueId;

    /** 属性值名称 */
    @Excel(name = "属性值名称")
    private String propValue;

    /** 属性ID */
    @Excel(name = "属性ID")
    private Long propId;

    public void setValueId(Long valueId) 
    {
        this.valueId = valueId;
    }

    public Long getValueId() 
    {
        return valueId;
    }
    public void setPropValue(String propValue) 
    {
        this.propValue = propValue;
    }

    public String getPropValue() 
    {
        return propValue;
    }
    public void setPropId(Long propId) 
    {
        this.propId = propId;
    }

    public Long getPropId() 
    {
        return propId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("valueId", getValueId())
            .append("propValue", getPropValue())
            .append("propId", getPropId())
            .append("createTime", getCreateTime())
            .toString();
    }
}
