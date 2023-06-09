package com.ts.shop.domain.dto;

import lombok.Data;

/**
 * @ClassName AppConfigDto
 * @Author TS SHOP
 * @create 2023/6/7
 */
@Data
public class AppConfigDto {
    /**
     * app 名称
     */
    private String appName;

    /**
     * app 图标
     */
    private String appIcon;

    /**
     * app 默认头像
     */
    private String defaultHeadPortrait;
}
