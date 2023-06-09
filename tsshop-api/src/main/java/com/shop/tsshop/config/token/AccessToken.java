package com.shop.tsshop.config.token;

import lombok.Data;

import java.util.Date;

/**
 * @author xu
 */
@Data
public class AccessToken {

    /**
     * 验证是否正确
     */
    private Boolean verify;
    /**
     * 通过token 获得用户手机号
     */
    private Long userId;

    /**
     * token 生成时间
     */
    private Date signDate;
    /**
     * token 失效时间
     */
    private Date expireDate;
    /**
     * 是否失效
     */
    private Boolean expire;


}
