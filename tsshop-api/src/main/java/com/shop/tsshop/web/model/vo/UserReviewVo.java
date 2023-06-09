package com.shop.tsshop.web.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserReviewVo {
    /**
     * id
     */
    private Long id;

    /**
     * 评分
     */
    private Integer score;

    /**
     * 评论内容
     */
    private String review;

    /**
     * 评论图片
     */
    private String imgs;

    /**
     * 匿名发布，0不匿名 1匿名
     */
    private Integer anonymous;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 购买数量
     */
    private Integer quantity;

    private String goodsName;

    private String src;

    private String sku;
    private Long orderId;
}
