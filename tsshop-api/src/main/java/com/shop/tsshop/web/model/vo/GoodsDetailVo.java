package com.shop.tsshop.web.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.shop.tsshop.web.model.domain.GoodsSku;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author GAGA
 */
@Data
public class GoodsDetailVo {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 商品图标
     */
    private String headPortrait;

    /**
     * 图文信息
     */
    private String detail;


    /**
     * 购买数量
     */
    private Integer purchaseQuantity;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 划线价格
     */
    private BigDecimal scribingPrice;

    private Integer stockNumber;

    private Integer isController;

    private Integer seckillSatus;//0:不是，秒杀商品，1：是秒杀但没开始，2：是秒杀已开始

    private Date seckillTime;
//    private List<GoodsSku> skuList;
}
