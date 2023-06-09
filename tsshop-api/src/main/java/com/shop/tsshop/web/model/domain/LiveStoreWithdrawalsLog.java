package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName LiveStoreWithdrawalsLog
 * @Author TS SHOP
 * @create 2023/5/23
 */

/**
    * 店铺提现记录
    */
@Data
@TableName(value = "live_store_withdrawals_log")
public class LiveStoreWithdrawalsLog {
    /**
     * 主键
     */
    @TableField(value = "id")
    private Long id;

    /**
     * 提现金额
     */
    @TableField(value = "amt")
    private BigDecimal amt;

    /**
     * 状态 0 到账 1 失败
     */
    @TableField(value = "status")
    private Boolean status;

    /**
     * 店铺id
     */
    @TableField(value = "live_store_id")
    private Long liveStoreId;

    /**
     * 提现方式
     */
    @TableField(value = "withdrawals_type")
    private String withdrawalsType;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 收款账户
     */
    @TableField(value = "account")
    private String account;

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
    /**
     * 提现单号
     */
    @TableField(value = "serial_number")
    private String serialNumber;

    /**
     * 失败原因
     */
    @TableField(value = "fail_reason")
    private String failReason;
    /**
     * 手续费
     */
    @TableField(value = "fees")
    private BigDecimal fees;
}