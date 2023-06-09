package com.shop.tsshop.web.model.vo;

import com.shop.tsshop.web.model.domain.Goods;
import lombok.Data;

import java.util.List;

@Data
public class StoreListVo {

    List<Goods> goodsList;

    /**
     * 店铺名字
     */
    private String name;


    /**
     * 头像
     */
    private String avatar;
}
