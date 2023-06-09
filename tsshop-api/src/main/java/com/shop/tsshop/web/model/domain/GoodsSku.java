package com.shop.tsshop.web.model.domain;

import java.math.BigDecimal;
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
 * 单品SKU表
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("goods_sku")
public class GoodsSku implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单品ID
     */
    @TableId(value = "sku_id", type = IdType.AUTO)
    private Long skuId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 销售属性组合字符串 格式是p1:v1;p2:v2
     */
    private String properties;

    /**
     * 原价
     */
    private BigDecimal oriPrice;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 实际库存
     */
    private Integer stocks;

    private Integer actualStocks;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 记录时间
     */
    private Date recTime;

    /**
     * 商家编码
     */
    private String partyCode;

    /**
     * sku图片
     */
    private String pic;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 0 启用 1 禁用
     */
    private Integer status;

    /**
     * 0 正常 1 已被删除
     */
    private Integer deleted;

    /**
     * 锁定库存数量
     */
    private Integer lockStocks;

    private BigDecimal costPrice;
}
