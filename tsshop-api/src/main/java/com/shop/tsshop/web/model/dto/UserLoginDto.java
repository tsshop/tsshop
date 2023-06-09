package com.shop.tsshop.web.model.dto;


import lombok.Data;

@Data
public class UserLoginDto {

    /**
     * 登录场景
     * login_password:phone+password
     * login_code:phone+code
     * login_openId:openId
     */
    private String scenario;

    private String phone;

    /**
     * 验证吗
     */
    private String code;

    private String password;

    private String openId;

    private String avatarUrl;
    private String name;
}
