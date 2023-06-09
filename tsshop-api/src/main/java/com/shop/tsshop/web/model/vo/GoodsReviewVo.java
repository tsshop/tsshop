package com.shop.tsshop.web.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class GoodsReviewVo {
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

    private String userName;
    private String avatar;
}
