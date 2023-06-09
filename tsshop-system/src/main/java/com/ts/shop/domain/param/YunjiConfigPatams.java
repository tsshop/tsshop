package com.ts.shop.domain.param;

import lombok.Data;

/**
 * @ClassName YunjiConfigPatams
 * @Author TS SHOP
 * @create 2023/6/7
 */
@Data
public class YunjiConfigPatams {
    private String accessKey;
    private String accessSecret;
    private String signCode;
    private String templateCode;
    private String classificationSecret;
}
