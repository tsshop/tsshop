package com.shop.tsshop.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginScenario {
    /**
     * 登录场景
     * login_password:phone+password
     * login_code:phone+code
     * login_openId:openId
     */
    WX_PASSWORD("login_wx_password", "登录参数:phone+password+openId"),
    WX_CODE("login_wx_code", "登录参数:phone+code+openId"),
    WX_OPENID("login_wx_openId", "登录参数:openId"),
    WX("login_wx", "登录参数:phone+openId+avatarUrl +name"),
    APP_PASSWORD("login_app_password","phone+password"),
    APP_CODE("login_app_code","phone+code");
    private String value;

    private String msg;

}
