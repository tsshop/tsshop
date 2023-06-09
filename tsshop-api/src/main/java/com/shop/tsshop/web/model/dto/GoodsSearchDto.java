package com.shop.tsshop.web.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GoodsSearchDto {
    //分类，关键词，销量，价格，价格区间
    /**
     * 分类
     */
    private Long typeId;

    /**
     * 关键词
     */
    private String keyword;

    /**
     *sort 0,低到高，1，高到低
     */
    private Integer sort;

    /**
     *分类类别 0：销量 ，1：价格
     */
    private Integer sortType;

    /**
     *价格区间 最高
     */
    private Integer priceMax;
    /**
     *价格区间 最低
     */
    private Integer priceMin;

    private Integer pageNumber;
    private Integer pageSize;

    private Long goodsId;
}
