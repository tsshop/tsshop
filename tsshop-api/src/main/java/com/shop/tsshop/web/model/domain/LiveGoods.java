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
 * 直播商品表;
 * @author : tsshop
 * @date : 2023-5-25
 */
@Data
@TableName("live_goods")
@Accessors(chain = true)
public class LiveGoods implements Serializable{

    /** 主键 */
    private Long id ;

    /** 直播id */
    private Long liveId ;

    /** 店铺 id */
    private Long liveStoreId ;

    /** 商城商品 id */
    private Long goodsId ;

    /** 状态 */
    private Integer status ;

    /** 创建时间 */
    private Date createTime ;

    /** 更新时间 */
    private Date updateTime ;

    /** 讲解状态 */
    private Integer explainStatus;

    private Integer salesVolume;

    @TableField(exist = false)
    private List<LiveGoodsSku> goodsSkuList;


    /**
     * 商品名称
     */
    @TableField(exist = false)
    private String name;

    /**
     * 商品图标
     */
    @TableField(exist = false)
    private String headPortrait;

    /**
     * 图文信息
     */
    @TableField(exist = false)
    private String detail;

    /**
     * 运费
     */
    @TableField(exist = false)
    private BigDecimal freight;

    @TableField(exist = false)
    private BigDecimal price;

    @TableField(exist = false)
    private BigDecimal oriPrice ;

}