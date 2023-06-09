package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName LiveStore
 * @Author TS SHOP
 * @create 2023/5/23
 */

/**
    * 直播小店
    */
@Data
@TableName(value = "live_store")
public class LiveStore {
    /**
     * 主键
     */
    @TableField(value = "id")
    private Long id;

    /**
     * 用戶id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 店铺名称
     */
    @TableField(value = "store_name")
    private String storeName;

    /**
     * 店铺电话
     */
    @TableField(value = "store_phone")
    private String storePhone;

    /**
     * 店铺头像
     */
    @TableField(value = "store_head_portrait")
    private String storeHeadPortrait;

    /**
     * 店铺收益
     */
    @TableField(value = "amt")
    private BigDecimal amt;

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
     * 删除标志
     */
    @TableField(value = "deleted")
    private Integer deleted;
}