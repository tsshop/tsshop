package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName LiveStoreWithdrawalsConfig
 * @Author TS SHOP
 * @create 2023/6/1
 */

/**
    * 店铺提现配置
    */
@Data
@TableName(value = "live_store_withdrawals_config")
public class LiveStoreWithdrawalsConfig {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 提现方式
     */
    @TableField(value = "withdraw_type")
    private String withdrawType;

    /**
     * 最小提现金额
     */
    @TableField(value = "min_withdraw_amt")
    private BigDecimal minWithdrawAmt;

    /**
     * 最小提现金额
     */
    @TableField(value = "max_withdraw_amt")
    private BigDecimal maxWithdrawAmt;

    /**
     * 允许提现 0 允许  1 不允许
     */
    @TableField(value = "allowable_withdrawal")
    private Boolean allowableWithdrawal;

    /**
     * 允许小数 0 允许  1 不允许
     */
    @TableField(value = "allowable_decimal")
    private Boolean allowableDecimal;

    /**
     * 提现周期 0 每天  1 每周  2 每月
     */
    @TableField(value = "withdraw_period")
    private Integer withdrawPeriod;

    /**
     * 提现次数
     */
    @TableField(value = "withdraw_num")
    private Integer withdrawNum;

    /**
     * 提现费率
     */
    @TableField(value = "withdraw_rate")
    private String withdrawRate;

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