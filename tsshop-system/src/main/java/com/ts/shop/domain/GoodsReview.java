package com.ts.shop.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ts.common.annotation.Excel;
import com.ts.common.core.domain.BaseEntity;

/**
 * 评论对象 goods_review
 * 
 * @author xu
 * @date 2023-02-24
 */
public class GoodsReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "id")
    private Long id;

    /** 商品id */
    @Excel(name = "商品id")
    private Long goodsId;

    /** 订单id */
    @Excel(name = "订单id")
    private Long orderId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 评分 */
    @Excel(name = "评分")
    private Integer score;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String review;

    /** 评论图片 */
    @Excel(name = "评论图片")
    private String imgs;

    /** 匿名发布，0不匿名 1匿名 */
    @Excel(name = "匿名发布，0不匿名 1匿名")
    private Integer anonymous;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setScore(Integer score) 
    {
        this.score = score;
    }

    public Integer getScore() 
    {
        return score;
    }
    public void setReview(String review) 
    {
        this.review = review;
    }

    public String getReview() 
    {
        return review;
    }
    public void setImgs(String imgs) 
    {
        this.imgs = imgs;
    }

    public String getImgs() 
    {
        return imgs;
    }
    public void setAnonymous(Integer anonymous) 
    {
        this.anonymous = anonymous;
    }

    public Integer getAnonymous() 
    {
        return anonymous;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("goodsId", getGoodsId())
            .append("orderId", getOrderId())
            .append("userId", getUserId())
            .append("score", getScore())
            .append("review", getReview())
            .append("imgs", getImgs())
            .append("anonymous", getAnonymous())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
