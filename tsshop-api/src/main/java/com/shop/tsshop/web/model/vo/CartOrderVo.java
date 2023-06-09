package com.shop.tsshop.web.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: xu
 **/
@Data
public class CartOrderVo {

    //合计 （运费+商品总价）
    private BigDecimal allMoney;

    //运费
    private BigDecimal freight;
    //商品总价
    private BigDecimal total;

    //商品列表
    private List<CartListVo> goodsList;
}
