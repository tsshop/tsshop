package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 弹幕对象 live_message
 *
 * @author ruoyi
 * @date 2023-06-05
 */
public class LiveMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 直播id */
    @Excel(name = "直播id")
    private Long liveId;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

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
    public void setLiveId(Long liveId)
    {
        this.liveId = liveId;
    }

    public Long getLiveId()
    {
        return liveId;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("liveId", getLiveId())
            .append("content", getContent())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
