package com.ts.shop.domain.dto;

import lombok.Data;

/**
 * @ClassName DayuMessageConfigDto
 * @Author TS SHOP
 * @create 2023/6/7
 */
@Data
public class DayuMessageConfigDto {
    private Boolean openStatus;
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String templateCode;
}
