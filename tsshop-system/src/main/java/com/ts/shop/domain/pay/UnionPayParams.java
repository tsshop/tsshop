package com.ts.shop.domain.pay;

import lombok.Data;

/**
 * @author : my
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
    private String weiXinPayUrl;

    //支付宝h5url
    private String aliPayUrl;
    // 云闪付支付地址
    private String unionPayUrl;


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

}
