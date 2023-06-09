package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName LiveGift
 * @Author TS SHOP
 * @create 2023/5/23
 */

/**
    * 直播礼物表
    */
@Data
@TableName(value = "live_gift")
public class LiveGift {
    /**
     * 主键
     */
    @TableField(value = "id")
    private Long id;

    /**
     * 显示顺序
     */
    @TableField(value = "gift_order_by")
    private Long giftOrderBy;

    /**
     * 礼物名称
     */
    @TableField(value = "gift_name")
    private String giftName;

    /**
     * 礼物封面
     */
    @TableField(value = "front")
    private String front;

    /**
     * 单价
     */
    @TableField(value = "unit_price")
    private BigDecimal unitPrice;

    /**
     * 删除标志
     */
    @TableField(value = "deleted")
    private Boolean deleted;
    /**
     * 上架状态
     */
    @TableField(value = "shelves")
    private Boolean shelves;

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

    @TableField(exist = false)
    private Long giftNum;

    @TableField(value = "gift_rate")
    private BigDecimal giftRate;
}