package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @ClassName LiveGiftDetail
 * @Author TS SHOP
 * @create 2023/5/23
 */

/**
    * 直播礼物赠送记录表
    */
@Data
@TableName(value = "live_gift_detail")
public class LiveGiftDetail {
    /**
     * 主键
     */
    @TableField(value = "id")
    private Long id;

    /**
     * 礼物 id
     */
    @TableField(value = "gift_id")
    private Long giftId;

    /**
     * 赠送用户 id
     */
    @TableField(value = "from_user_id")
    private Long fromUserId;

    /**
     * 被赠送用户id
     */
    @TableField(value = "to_user_id")
    private Long toUserId;

    /**
     * 赠送数量
     */
    @TableField(value = "number")
    private Long number;

    /**
     * 直播 id
     */
    @TableField(value = "live_id")
    private Long liveId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 礼物名称
     */
    @TableField(exist = false)
    private String giftName;
}