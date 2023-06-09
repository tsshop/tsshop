package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 直播礼物赠送记录对象 live_gift_detail
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
public class LiveGiftDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    private Long id;

    /** 礼物 id */
    @Excel(name = "礼物 id")
    private Long giftId;

    /** 赠送用户 id */
    @Excel(name = "赠送用户 id")
    private Long fromUserId;

    /** 被赠送用户id */
    @Excel(name = "被赠送用户id")
    private Long toUserId;

    /** 赠送数量 */
    @Excel(name = "赠送数量")
    private Long number;

    /** 直播 id */
    @Excel(name = "直播 id")
    private Long liveId;

    /** 礼物名称 */
    @Excel(name = "礼物名称")
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
    public void setGiftId(Long giftId) 
    {
        this.giftId = giftId;
    }

    public Long getGiftId() 
    {
        return giftId;
    }
    public void setFromUserId(Long fromUserId) 
    {
        this.fromUserId = fromUserId;
    }

    public Long getFromUserId() 
    {
        return fromUserId;
    }
    public void setToUserId(Long toUserId) 
    {
        this.toUserId = toUserId;
    }

    public Long getToUserId() 
    {
        return toUserId;
    }
    public void setNumber(Long number) 
    {
        this.number = number;
    }

    public Long getNumber() 
    {
        return number;
    }
    public void setLiveId(Long liveId) 
    {
        this.liveId = liveId;
    }

    public Long getLiveId() 
    {
        return liveId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("giftId", getGiftId())
            .append("fromUserId", getFromUserId())
            .append("toUserId", getToUserId())
            .append("number", getNumber())
            .append("liveId", getLiveId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
