package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName LiveLink
 * @Author TS SHOP
 * @create 2023/5/23
 */

/**
    * 直播连麦
    */
@Data
@TableName(value = "live_link")
public class LiveLink {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 直播id
     */
    @TableField(value = "live_id")
    private Long liveId;

    @TableField(value = "is_from_user")
    private Boolean isFromUser;

    @TableField(value = "start_time")
    private Date startTime ;

    @TableField(value = "end_time")
    private Date endTime ;

    /**
     * 直播用户id
     */
    @TableField(value = "live_user_id")
    private Long liveUserId;

    /**
     * 状态
     */
    @TableField(value = "status")
    private Integer status;

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
    private Long roomId;

    /** 直播人名称 */
    @TableField(exist = false)
    private String userName ;

    /** 直播人头像 */
    @TableField(exist = false)
    private String userAvatarUrl ;
}