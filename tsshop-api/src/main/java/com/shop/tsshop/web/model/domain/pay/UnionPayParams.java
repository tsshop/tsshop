package com.shop.tsshop.web.model.domain.pay;

import lombok.Data;

/**
 * @author : tsshop
 * @date : 2023/4/23
 */
@Data
public class UnionPayParams {

    //appId
    private String appId;
    //appKey
    private String appKey;

    //mid
    private String mid;

    //tid
    private String tid;

    //通讯密钥
    private String secretKey;

    //微信小程序url
    private String weChatLiteUrl;

    //支付宝h5url
    private String aliPayH5Url;

    //支付宝小程序 url
    private String aliPayLiteUrl;
    // 云闪付支付地址
    private String unionPayUrl;
    // 微信子商戶ID
    private String subAppId;
     //回调地址
    private String notifyUrl;

    //微信小程序appid
    private String wxAppId;

    //微信小程序Secret
    private String wxAppSecret;

    //订单前缀
    private String orderPrefix;

    //退款地址
    private String refundUrl;

    // 支付宝小程序 回调 APP scheme
    private String schemeUrl;

    private String weChatLiteSecret;

}
