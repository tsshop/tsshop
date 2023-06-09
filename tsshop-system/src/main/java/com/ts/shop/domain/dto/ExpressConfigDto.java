package com.ts.shop.domain.dto;

import lombok.Data;

/**
 * @ClassName ExpressConfigDto 快递配置
 * @Author TS SHOP
 * @create 2023/6/6
 */
@Data
public class ExpressConfigDto {
    private String enable;
    private String appId;
    private String appKey;
    private String reqUrl;
}
