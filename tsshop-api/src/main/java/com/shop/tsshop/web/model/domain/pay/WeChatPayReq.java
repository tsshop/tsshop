package com.shop.tsshop.web.model.domain.pay;

import lombok.Data;

/**
 * @ClassName WechatPayReq
 * @Author TS SHOP
 * @create 2023/5/21
 */
@Data
public class WeChatPayReq {
    /**
     * 应用ID
     */
    private String appid;
    /**
     * 直连商户号
     */
    private String mchid;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 回调地址
     */
    private String notify_url;
    /**
     * 订单金额信息
     */
    private String amount;
}
