package com.shop.tsshop.web.netty.session;

import lombok.Data;

/**
 * @Desc :
 * @Create : tsshop ~ 2020/06/13
 */
@Data
public class Session {

    /** 用户ID */
    private String id;
    /** 用户名 */
    private String name;
    /** 在线状态 {online,offline} */
    private String status;
    /** 签名 */
    private String sign;
    /** 头像 */
    private String avatarUrl;

    public Session() {
    }

}
