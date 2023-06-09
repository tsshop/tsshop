package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName LiveReserve
 * @Author TS SHOP
 * @create 2023/5/23
 */

/**
    * 直播预约
    */
@Data
@TableName(value = "live_reserve")
public class LiveReserve {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 预约id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 直播id
     */
    @TableField(value = "live_id")
    private Long liveId;

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