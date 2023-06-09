package com.ts.shop.domain.dto;

import com.ts.shop.domain.param.AliOssConfigParams;
import lombok.Data;

/**
 * @ClassName StorageConfigDto
 * @Author TS SHOP
 * @create 2023/6/6
 */
@Data
public class StorageConfigDto {
    /**
     * 存储类型
     */
    private String storageType;

    /**
     * 配置信息
     */
    private AliOssConfigParams aliOssConfigDto;
}
