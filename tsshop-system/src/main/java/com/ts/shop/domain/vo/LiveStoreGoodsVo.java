package com.ts.shop.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName LiveStoreGoodsVo
 * @Author TS SHOP
 * @create 2023/5/29
 */
@Data
public class LiveStoreGoodsVo {
    /**
     * 商品ID
     */
    private Long id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品主图
     */
    private String headPortrait;
    /**
     * 划线价格
     */
    private BigDecimal scribingPrice;
    /**
     * 售价
     */
    private BigDecimal price;
    /**
     * 订单量
     */
    private Long orderNum;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
