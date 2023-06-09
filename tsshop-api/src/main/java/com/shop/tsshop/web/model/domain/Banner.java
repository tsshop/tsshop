package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户首页轮播图片表
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 轮播图id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 图片url
     */
    private String imgUrl;

    /**
     * 跳转地址
     */
    private String linkedUrl;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    private Boolean deleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 排序
     */
    private Integer sort;


}
