package com.shop.tsshop.web.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 直播商品信息表;
 * @author : tsshop
 * @date : 2023-5-25
 */
@Data
@TableName("live_goods_sku")
@Accessors(chain = true)
public class LiveGoodsSku implements Serializable{

    /** 主键 */
    private Long id ;

    /** 直播id */
    private Long liveId ;

    /** 店铺id */
    private Long liveStoreId ;

    /** 直播商品id */
    private Long liveGoodsId ;

    /** sku_id */
    private Long skuId ;

    /** 库存 */
    private Integer stocks ;

    /** 商品原价 */
    private BigDecimal oriPrice ;

    /** 现价 */
    private BigDecimal price ;

    /** 创建时间 */
    private Date createTime ;

    /** 更新时间 */
    private Date updateTime ;

    @TableField(exist = false)
    private String properties;

    @TableField(exist = false)
    private String pic;

    @TableField(exist = false)
    private String skuName;

    @TableField(exist = false)
    private String prodName;

    @TableField(exist = false)
    private Long goodsId;

}