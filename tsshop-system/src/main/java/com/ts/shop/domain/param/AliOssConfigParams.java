package com.ts.shop.domain.param;

import lombok.Data;

/**
 * @ClassName AliOssConfigDto
 * @Author TS SHOP
 * @create 2023/6/6
 */
@Data
public class AliOssConfigParams {
    private String endpoint;
    private String endpointECS;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String filedir;
    private String domain;
    private String host;
    private int expireTime;
    private long maxFileSize;
}
