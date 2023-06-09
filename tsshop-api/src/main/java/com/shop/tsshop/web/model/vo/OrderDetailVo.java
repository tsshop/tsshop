package com.shop.tsshop.web.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.shop.tsshop.web.model.domain.AddressOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDetailVo {
    /**
     * 订单ID
     */
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
     * (0, "待付款"),(1, "待发货"),(3, "已完成"),(4, "待收货"),(-1, "已取消");
     */
    private Integer orderStatus;

    /**
     * 下单时间
     */
    private Date payTime;

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

    private Long isReview;


    /**
     * 创建时间/下单时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;



    private AddressOrder addressOrder;
    private String goodsName;
    private String src;
    private String sku;
}
