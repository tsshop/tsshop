package com.shop.tsshop.web.model.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author MI
 */
@Data
public class OrderDto implements Serializable {

    //订单id")
    private Long orderId;
    /**
     * 用户id
     */
    //用户id")
    private Long userId;

    /**
     * 商品id
     */
    @NotNull(message = "商品id不能为空")
    //商品id")
    private Long goodsId;

    /**
     * 地址id
     */
    @NotNull(message = "地址id不能为空")
    //地址id")
    private Long addressId;

    /**
     * 订单状态
     */
    private Integer orderStatus;


    private String ip;

    /**
     * 购买数量
     */
    //购买数量")
    private Integer quantity;

    /**
     * 区
     */
    //区")
    private Long area;

    /**
     * skuId
     */
    //skuId")
    private Long skuId;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 直播小店ID
     */
    private Long liveStoreId;


    private Long liveId;

}
