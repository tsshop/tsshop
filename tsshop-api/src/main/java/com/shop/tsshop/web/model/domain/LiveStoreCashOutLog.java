package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName LiveStoreCashOutLog
 * @Author TS SHOP
 * @create 2023/6/4
 */

/**
    * 支付宝转账订单记录表
    */
@Data
@TableName(value = "live_store_cash_out_log")
public class LiveStoreCashOutLog {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 支付宝转账订单号
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 商户订单号
     */
    @TableField(value = "out_biz_no")
    private String outBizNo;

    /**
     * 支付宝支付资金流水号
     */
    @TableField(value = "pay_fund_order_id")
    private String payFundOrderId;

    /**
     * 转账单据状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 错误状态码
     */
    @TableField(value = "error_code")
    private String errorCode;

    /**
     * 错误原因
     */
    @TableField(value = "error_reason")
    private String errorReason;

    /**
     * 订单支付时间
     */
    @TableField(value = "trans_date")
    private Date transDate;

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