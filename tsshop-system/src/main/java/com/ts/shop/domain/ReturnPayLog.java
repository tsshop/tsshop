package com.ts.shop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName ReturnPayLog
 * @Author TS SHOP
 * @create 2023/5/24
 */

/**
    * 退款记录表
    */
@Data
@TableName(value = "return_pay_log")
public class ReturnPayLog {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    private String orderNo;

    /**
     * 退款单 ID
     */
    @TableField(value = "return_order_id")
    private Long returnOrderId;

    /**
     * 退款金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 退款类型
     */
    @TableField(value = "return_pay_type")
    private String returnPayType;

    /**
     * 退款通道ID
     */
    @TableField(value = "return_pay_thoroughfare_id")
    private Long returnPayThoroughfareId;

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