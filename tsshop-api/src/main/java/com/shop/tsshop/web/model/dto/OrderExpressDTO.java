package com.shop.tsshop.web.model.dto;

import lombok.Data;

/**
 * @Author: fuhuilei
 * @Date: 2022-04-29 14:22
 **/
@Data
public class OrderExpressDTO {

    /**
     * 物流公司
     */
   //物流公司")
    private String express;

    /**
     * 物流单号
     */
   //物流单号")
    private String expressNo;

    /**
     * 订单编号
     */
   //订单编号")
    private String orderNo;
}
