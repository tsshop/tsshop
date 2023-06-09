package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 存储配置对象 tsshop_config_storage
 * 
 * @author ruoyi
 * @date 2023-06-06
 */
public class TsshopConfigStorage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 开启状态 */
    @Excel(name = "开启状态")
    private Boolean openStatus;

    public Boolean getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Boolean openStatus) {
        this.openStatus = openStatus;
    }

    /** 存储类型 */
    @Excel(name = "存储类型")
    private String storageType;

    /** 存储位置 */
    @Excel(name = "存储位置")
    private String storageLocation;

    /** 配置信息 */
    @Excel(name = "配置信息")
    private String config;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setStorageType(String storageType) 
    {
        this.storageType = storageType;
    }

    public String getStorageType() 
    {
        return storageType;
    }
    public void setStorageLocation(String storageLocation) 
    {
        this.storageLocation = storageLocation;
    }

    public String getStorageLocation() 
    {
        return storageLocation;
    }
    public void setConfig(String config) 
    {
        this.config = config;
    }

    public String getConfig() 
    {
        return config;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("openStatus", getOpenStatus())
            .append("storageType", getStorageType())
            .append("storageLocation", getStorageLocation())
            .append("config", getConfig())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
