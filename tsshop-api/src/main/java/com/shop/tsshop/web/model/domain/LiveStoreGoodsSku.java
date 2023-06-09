package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @ClassName LiveStoreGoodsSku
 * @Author TS SHOP
 * @create 2023/5/23
 */

/**
    * 直播小店商品信息表（SKU）
    */
@Data
@TableName(value = "live_store_goods_sku")
public class LiveStoreGoodsSku {
    /**
     * 主键
     */
    @TableField(value = "id")
    private Long id;
    /**
     * SPU ID
     */
    @TableField(value = "live_store_goods_id")
    private Long liveStoreGoodsId;

    /**
     * sku_id
     */
    @TableField(value = "sku_id")
    private Long skuId;

    /**
     * 商品原价
     */
    @TableField(value = "ori_price")
    private BigDecimal oriPrice;

    /**
     * 现价
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 删除标志 0 未删除 1 删除
     */
    @TableField(value = "deleted")
    private Boolean deleted;

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
    /**
     * 店铺 ID
     */
    @TableField(value = "edit_flag")
    private Integer editFlag;
    /**
     * Sku 信息
     */
    @TableField(exist = false)
    private String skuName;

    /**
     * Sku 库存
     */
    @TableField(exist = false)
    private Integer stocks;
}