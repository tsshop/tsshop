package com.shop.tsshop.web.model.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author xu
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`order`")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 单品ID
     */
    private Long skuId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 收货地址ID
     */
    private Long addressId;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 订单价格
     */
    private BigDecimal amount;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * (0, "待付款"),(1, "待发货"),(2, "待收货"),(3, "已完成"),(4, "待收货"),(-1, "已取消");
     */
    private Integer orderStatus;

    /**
     * 下单时间
     */
    private Date payTime;

    /**
     * 收货时间
     */
    private Date sendTime;

    /**
     * 支付交易号
     */
    private String payNo;

    /**
     * 物流单号
     */
    private String expressNo;

    /**
     * 物流编码
     */
    private String express;

    /**
     * 实际支付订单编号
     */
    private String orderNoPay;

    /**
     * 退款单号
     */
    private Long returnId;

    /**
     * 备注
     */
    private String remark;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 创建时间/下单时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 是否已删除 （1：正常/-1：删除）
     */
    private Integer deleted;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 支付通道ID
     */
    private Long payThoroughfareId;

    /**
     * 直播小店 ID
     */
    private Long liveStoreId;

    /**
     * 支付记录 ID
     */
    private Long payLogId;


    private Long liveId;

    /**
     * 订单类型 0 商品购买 1 礼物购买
     */
    private Integer orderType;


}
