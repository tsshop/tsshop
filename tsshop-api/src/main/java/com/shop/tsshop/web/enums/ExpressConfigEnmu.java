package com.shop.tsshop.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ExpressConfigEnmu
 * @Author TS SHOP
 * @create 2023/6/6
 */
@Getter
@AllArgsConstructor
public enum ExpressConfigEnmu {
    KDNIAO_ENABLE("KDNIAO_ENABLE","enable"),
    KDNIAO_APPID("KDNIAO_APPID","appId"),
    KDNIAO_APPKEY("KDNIAO_APPKEY","appKey"),
    KDNIAO_REQURL("KDNIAO_REQURL","ReqURL");
    private String code;
    private String msg;
}
