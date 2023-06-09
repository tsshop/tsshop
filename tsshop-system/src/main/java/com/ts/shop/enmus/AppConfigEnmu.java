package com.ts.shop.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName AppConfigEnmu
 * @Author TS SHOP
 * @create 2023/6/7
 */
@Getter
@AllArgsConstructor
public enum AppConfigEnmu {
    APP_NAME("APP_NAME","APP名称"),
    APP_ICON("APP_ICON","APP图标"),
    APP_DEFAULT_HEAD_PORTRAIT("APP_DEFAULT_HEAD_PORTRAIT","默认头像");
    private String code;
    private String msg;
}
