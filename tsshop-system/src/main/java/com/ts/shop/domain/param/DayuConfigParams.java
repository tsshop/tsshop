package com.ts.shop.domain.param;

import lombok.Data;

/**
 * @ClassName DayuConfigParams
 * @Author TS SHOP
 * @create 2023/6/7
 */
@Data
public class DayuConfigParams {
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String templateCode;
}
