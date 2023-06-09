package com.shop.tsshop.web.model.domain;

import java.math.BigDecimal;
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
 * 商品表
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 售价
     */
    private BigDecimal price;

    /**
     * 审核状态 1下架 2上架
     */
    private Integer shelves;

    /**
     * 是否已删除 0-未删除 1-已删除
     */
    private Integer deleted;

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
     * 商品主图
     */
    private String headPortrait;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 销售量
     */
    private Long purchaseQuantity;

    /**
     * 商品分类主键
     */
    private Long goodsTypeId;

    /**
     * 货号
     */
    private String csno;

    /**
     * 单位
     */
    private String unitName;

    /**
     * 划线价格
     */
    private BigDecimal scribingPrice;

    /**
     * 库存
     */
    private Integer stockNumber;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 运费模版id
     */
    private Long deliveryTemplateId;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 体积
     */
    private BigDecimal volume;

    /**
     * 城市id
     */
    private Long areaId;

    /**
     * 店铺改动
     */
    private Long storeId;


}
