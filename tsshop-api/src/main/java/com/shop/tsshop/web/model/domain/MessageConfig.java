package com.shop.tsshop.web.model.domain;

import lombok.Data;

/**
 * @ClassName MessageConfig
 * @Author TS SHOP
 * @create 2023/6/7
 */
@Data
public class MessageConfig {
    /**
     * 短信类型
     */
    private String type;

    /**
     * 配置信息
     */
    private String config;
}
