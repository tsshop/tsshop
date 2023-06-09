package com.shop.tsshop.web.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author xu
 * @since 2022-09-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CartListVo implements Serializable {

    /**
     * 订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 单价
     */
    private BigDecimal amt;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    private Integer status;//0:正常，1：商品已下架，2，商品没库存，3，sku没库存

    /**
     * 购买数量
     */
    private Integer purchaseQuantity;

    /**
     * 单品ID
     */
    private Long skuId;


    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 创建时间/下单时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private String name;
    private String img;
    private Long deliveryTemplateId;
    /**
     * 销售属性组合字符串 格式是p1:v1;p2:v2
     */
    private String properties;
    private BigDecimal price;
    private Integer sStocks;//sku库存
    private Integer gStocks;//主商品库存
    private Integer gShelves;//0正常
    private Integer sShelves;//0正常
}
