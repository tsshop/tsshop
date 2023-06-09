package com.ts.shop.domain.param;

import com.ts.common.annotation.Excel;
import com.ts.shop.domain.GoodsSku;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GoodsUpdateDTO {
    /** 商品id */
    private Long id;

    /** 商品名称 */
    private String name;

    /** 售价 */
    private BigDecimal price;

    /** 审核状态 1下架 2上架 */
    private Integer shelves;

    /** 是否已删除 0-未删除 1-已删除 */
    private Integer deleted;

    /** 商品主图 */
    private String headPortrait;

    /** 商品详情 */
    private String detail;

    /** 销售量 */
    private Long purchaseQuantity;

    /** 商品分类主键 */
    private Long goodsTypeId;

    /** 货号 */
    private String csno;

    /** 单位 */
    private String unitName;

    /** 划线价格 */
    private BigDecimal scribingPrice;

    /** 库存 */
    private Long stockNumber;

    /** 排序 */
    private Long sort;

    /** 运费模版id */
    private Long deliveryTemplateId;

    /** 重量 */
    private BigDecimal weight;

    /** 体积 */
    private BigDecimal volume;

    /** 城市id */
    private Long areaId;

    /** 店铺改动 */
    private Long storeId;
    /**
     * sku
     */
    private List<GoodsSku> skuList;
}
