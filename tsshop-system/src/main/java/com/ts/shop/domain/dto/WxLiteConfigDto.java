package com.ts.shop.domain.dto;

import lombok.Data;

/**
 * @ClassName WxLiteConfigDto
 * @Author TS SHOP
 * @create 2023/6/8
 */
@Data
public class WxLiteConfigDto {
    /**
     * 小程序 ID
     */
    private String appid;
    /**
     * 小程序密钥
     */
    private String appSecret;
}
