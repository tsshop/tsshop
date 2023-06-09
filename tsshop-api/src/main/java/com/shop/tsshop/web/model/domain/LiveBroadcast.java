package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 直播记录表;
 * @author : tsshop
 * @date : 2023-5-23
 */
@Data
@TableName("live_broadcast")
@Accessors(chain = true)
public class LiveBroadcast implements Serializable{

    /** id */
    private Long id ;

    /** 店铺id */
    private Long liveStoreId;

    /** 直播人id */
    private Long userId ;

    /** 房间号 */
    private Long roomId ;

    /** 预计开始时间 */
    private Date reckonTime ;

    /** 开始时间 */
    private Date startTime ;

    /** 结束时间 */
    private Date endTime ;

    /** 状态 1：未开始 2：直播中 3：直播结束 4：异常结束 */
    private Integer status ;

    /** 连麦数 */
    private Integer linkNum ;

    /** 商品数量 */
    private Integer goodsNum ;

    /** 观看人数 */
    private Long personNum ;

    /** 点赞数 */
    private Long praiseNum ;

    /** 订单收益 */
    private BigDecimal orderProfit ;

    /** 礼物收益 */
    private BigDecimal giftProfit ;

    /** 是否结算 */
    private Boolean isCash ;

    /** 直播标题 */
    private String title ;

    /** 封面 */
    private String url ;

    private String describes;

    /** 创建时间 */
    private Date createTime ;

    /** 更新时间 */
    private Date updateTime ;

    private Long giftNum;

    private Long orderNum;

    /** 直播人名称 */
    @TableField(exist = false)
    private String userName ;

    /** 直播人头像 */
    @TableField(exist = false)
    private String userAvatarUrl ;

    /** 直播人头像 */
    @TableField(exist = false)
    private Boolean isReserve;

    @TableField(exist = false)
    private List<LiveGoods> goodsList;

    @TableField(exist = false)
    private String liveStoreName;

    @TableField(exist = false)
    private String between;

    @TableField(exist = false)
    private LiveLink liveLink;
}