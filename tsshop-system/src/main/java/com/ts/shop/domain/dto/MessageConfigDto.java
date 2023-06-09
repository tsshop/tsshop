package com.ts.shop.domain.dto;

import lombok.Data;

/**
 * @ClassName MessageConfigDto
 * @Author TS SHOP
 * @create 2023/6/6
 */
@Data
public class MessageConfigDto {
    private Boolean openStatus;
    private String accessKey;
    private String accessSecret;
    private String signCode;
    private String templateCode;
    private String classificationSecret;
}
