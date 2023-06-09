package com.ts.shop.domain.pay;

import lombok.Data;

/**
 * @ClassName WeChatAppParams
 * @Author TS SHOP
 * @create 2023/5/19
 */
@Data
public class WeChatAppParams {
    /**
     * 应用 ID
     */
    private String appid;
    /**
     * 直连商户号
     */
    private String mchid;
    /**
     * 回调域名
     */
    private String notify_url;
    /**
     * 微信支付证书  P12
     */
    private String certPath;
    /**
     * 应用私钥
     */
    private String keyPath;
    /**
     * 微信 APIv3密钥
     */
    private String mchKey;
    /**
     * 序列号
     */
    private String serialNo;

}
