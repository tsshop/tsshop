package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName PayLog
 * @Author TS SHOP
 * @create 2023/5/24
 */

/**
    * 支付记录表
    */
@Data
@TableName(value = "pay_log")
public class PayLog {
    /**
     * 主键
     */
    @TableField(value = "id")
    private Long id;

    /**
     * 支付交易号
     */
    @TableField(value = "pay_no")
    private String payNo;

    /**
     * 支付时间
     */
    @TableField(value = "pay_time")
    private Date payTime;

    /**
     * 付款金额
     */
    @TableField(value = "pay_amount")
    private BigDecimal payAmount;

    /**
     * 支付商家订单编号
     */
    @TableField(value = "order_no_pay")
    private String orderNoPay;

    /**
     * 支付类型
     */
    @TableField(value = "pay_type")
    private String payType;

    /**
     * 支付通道 ID
     */
    @TableField(value = "pay_thoroughfare_id")
    private Long payThoroughfareId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
}