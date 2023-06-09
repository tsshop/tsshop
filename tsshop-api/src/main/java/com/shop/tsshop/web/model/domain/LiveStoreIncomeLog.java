package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName LiveStoreIncomeLog
 * @Author TS SHOP
 * @create 2023/5/23
 */

/**
    * 店铺收入记录
    */
@Data
@TableName(value = "live_store_income_log")
public class LiveStoreIncomeLog {
    /**
     * 主键
     */
    @TableField(value = "id")
    private Long id;

    /**
     * 直播小店id
     */
    @TableField(value = "live_store_id")
    private Long liveStoreId;

    /**
     * 收入金额
     */
    @TableField(value = "amt")
    private BigDecimal amt;

    /**
     * 收入类型 0 直播收益 1 订单收益
     */
    @TableField(value = "income_type")
    private Integer incomeType;

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