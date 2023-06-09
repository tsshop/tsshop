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
 * 购物车表
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop_cart")
public class ShopCart implements Serializable {

    private static final long serialVersionUID = 1L;

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

    private Boolean status;

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

    /**
     * 是否已删除 （1：正常/-1：删除）
     */
    private Boolean deleted;


}
