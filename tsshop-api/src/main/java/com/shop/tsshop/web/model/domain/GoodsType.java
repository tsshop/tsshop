package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * 商品类型表
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("goods_type")
public class GoodsType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品类型ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 商品类型图标
     */
    private String avatar;

    private String banner;

    /**
     * 商品类别名称
     */
    private String name;

    /**
     * 是否已删除 （1：正常/-1：删除）
     */
    private Boolean deleted;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父分类id，0为一级
     */
    private Long pTypeId;

    /**
     * 级别
     */
    private Integer level;


}
