package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 配置对象 config
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public class Config extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private Long id;

    /** 键 */
    @Excel(name = "键")
    private String configKey;

    /** 值 */
    @Excel(name = "值")
    private String configValue;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 1:显示 0：不显示 */
    @Excel(name = "1:显示 0：不显示")
    private Integer isFront;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setConfigKey(String configKey) 
    {
        this.configKey = configKey;
    }

    public String getConfigKey() 
    {
        return configKey;
    }
    public void setConfigValue(String configValue) 
    {
        this.configValue = configValue;
    }

    public String getConfigValue() 
    {
        return configValue;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setIsFront(Integer isFront) 
    {
        this.isFront = isFront;
    }

    public Integer getIsFront() 
    {
        return isFront;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("configKey", getConfigKey())
            .append("configValue", getConfigValue())
            .append("title", getTitle())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isFront", getIsFront())
            .toString();
    }
}
