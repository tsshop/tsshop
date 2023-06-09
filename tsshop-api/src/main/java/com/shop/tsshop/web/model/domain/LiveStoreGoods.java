package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName LiveStoreGoods
 * @Author TS SHOP
 * @create 2023/5/23
 */

/**
    * 直播小店商品表
    */
@Data
@TableName(value = "live_store_goods")
public class LiveStoreGoods {
    /**
     * 主键
     */
    @TableField(value = "id")
    private Long id;

    /**
     * 商城商品 id
     */
    @TableField(value = "goods_id")
    private Long goodsId;

    /**
     * 排序
     */
    @TableField(value = "order_by")
    private Integer orderBy;

    /**
     * 删除标志 0 未删除 1 已删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

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
     * 店铺 ID
     */
    @TableField(value = "live_store_id")
    private Long liveStoreId;
}