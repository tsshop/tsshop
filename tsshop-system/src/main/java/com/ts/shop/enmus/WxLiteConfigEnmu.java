package com.ts.shop.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName WxLiteConfigEnmu
 * @Author TS SHOP
 * @create 2023/6/8
 */
@Getter
@AllArgsConstructor
public enum WxLiteConfigEnmu {
    WEI_XIN_LITE_APPID("WEI_XIN_LITE_APPID","微信小程序 ID"),
    WEI_XIN_LITE_APPSECRET("WEI_XIN_LITE_APPSECRET","微信小程序密钥");
    private String code;
    private String msg;
}
