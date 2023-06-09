package com.ts.shop.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 直播记录对象 live_broadcast
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
public class LiveBroadcast extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private Long id;

    /** 店铺id */
    @Excel(name = "店铺id")
    private Long liveStoreId;

    /** 直播人id */
    @Excel(name = "直播人id")
    private Long userId;

    /** 房间号 */
    @Excel(name = "房间号")
    private Long roomId;

    /** 预计开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reckonTime;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 状态 1：未开始 2：直播中 3：直播结束 4：异常结束 */
    @Excel(name = "状态 1：未开始 2：直播中 3：直播结束 4：异常结束")
    private Long status;

    /** 连麦数 */
    @Excel(name = "连麦数")
    private Long linkNum;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private Long goodsNum;

    /** 观看人数 */
    @Excel(name = "观看人数")
    private Long personNum;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long praiseNum;

    /** 订单收益 */
    @Excel(name = "订单收益")
    private BigDecimal orderProfit;

    /** 礼物收益 */
    @Excel(name = "礼物收益")
    private BigDecimal giftProfit;

    /** 礼物数 */
    @Excel(name = "礼物数")
    private Long giftNum;

    /** 是否结算 */
    @Excel(name = "是否结算")
    private Integer isCash;

    /** 直播标题 */
    @Excel(name = "直播标题")
    private String title;

    /** 封面 */
    @Excel(name = "封面")
    private String url;

    /** 描述 */
    @Excel(name = "描述")
    private String describes;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLiveStoreId(Long liveStoreId) 
    {
        this.liveStoreId = liveStoreId;
    }

    public Long getLiveStoreId() 
    {
        return liveStoreId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setRoomId(Long roomId) 
    {
        this.roomId = roomId;
    }

    public Long getRoomId() 
    {
        return roomId;
    }
    public void setReckonTime(Date reckonTime) 
    {
        this.reckonTime = reckonTime;
    }

    public Date getReckonTime() 
    {
        return reckonTime;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setLinkNum(Long linkNum) 
    {
        this.linkNum = linkNum;
    }

    public Long getLinkNum() 
    {
        return linkNum;
    }
    public void setGoodsNum(Long goodsNum) 
    {
        this.goodsNum = goodsNum;
    }

    public Long getGoodsNum() 
    {
        return goodsNum;
    }
    public void setPersonNum(Long personNum) 
    {
        this.personNum = personNum;
    }

    public Long getPersonNum() 
    {
        return personNum;
    }
    public void setPraiseNum(Long praiseNum) 
    {
        this.praiseNum = praiseNum;
    }

    public Long getPraiseNum() 
    {
        return praiseNum;
    }
    public void setOrderProfit(BigDecimal orderProfit) 
    {
        this.orderProfit = orderProfit;
    }

    public BigDecimal getOrderProfit() 
    {
        return orderProfit;
    }
    public void setGiftProfit(BigDecimal giftProfit) 
    {
        this.giftProfit = giftProfit;
    }

    public BigDecimal getGiftProfit() 
    {
        return giftProfit;
    }
    public void setGiftNum(Long giftNum) 
    {
        this.giftNum = giftNum;
    }

    public Long getGiftNum() 
    {
        return giftNum;
    }
    public void setIsCash(Integer isCash) 
    {
        this.isCash = isCash;
    }

    public Integer getIsCash() 
    {
        return isCash;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setDescribes(String describes) 
    {
        this.describes = describes;
    }

    public String getDescribes() 
    {
        return describes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("liveStoreId", getLiveStoreId())
            .append("userId", getUserId())
            .append("roomId", getRoomId())
            .append("reckonTime", getReckonTime())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("status", getStatus())
            .append("linkNum", getLinkNum())
            .append("goodsNum", getGoodsNum())
            .append("personNum", getPersonNum())
            .append("praiseNum", getPraiseNum())
            .append("orderProfit", getOrderProfit())
            .append("giftProfit", getGiftProfit())
            .append("giftNum", getGiftNum())
            .append("isCash", getIsCash())
            .append("title", getTitle())
            .append("url", getUrl())
            .append("describes", getDescribes())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
