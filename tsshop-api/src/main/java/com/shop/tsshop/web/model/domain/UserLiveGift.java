package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName UserLiveGift
 * @Author TS SHOP
 * @create 2023/5/23
 */

/**
    * 用户礼物表
    */
@Data
@TableName(value = "user_live_gift")
public class UserLiveGift {
    /**
     * 主键
     */
    @TableField(value = "id")
    private Long id;

    /**
     * 用户 id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 礼物 id
     */
    @TableField(value = "live_gift_id")
    private Long liveGiftId;

    /**
     * 礼物数量
     */
    @TableField(value = "live_gift_num")
    private Long liveGiftNum;

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