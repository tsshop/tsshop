package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 用户礼物对象 user_live_gift
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
public class UserLiveGift extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    private Long id;

    /** 用户 id */
    @Excel(name = "用户 id")
    private Long userId;

    /** 礼物 id */
    @Excel(name = "礼物 id")
    private Long liveGiftId;

    /** 礼物数量 */
    @Excel(name = "礼物数量")
    private String liveGiftNum;

    private String liveGiftName;

    public String getLiveGiftName() {
        return liveGiftName;
    }

    public void setLiveGiftName(String liveGiftName) {
        this.liveGiftName = liveGiftName;
    }

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
    public void setLiveGiftId(Long liveGiftId) 
    {
        this.liveGiftId = liveGiftId;
    }

    public Long getLiveGiftId() 
    {
        return liveGiftId;
    }
    public void setLiveGiftNum(String liveGiftNum) 
    {
        this.liveGiftNum = liveGiftNum;
    }

    public String getLiveGiftNum() 
    {
        return liveGiftNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("liveGiftId", getLiveGiftId())
            .append("liveGiftNum", getLiveGiftNum())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
