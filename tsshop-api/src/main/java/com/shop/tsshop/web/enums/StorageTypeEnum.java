package com.shop.tsshop.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName StorageType
 * @Author TS SHOP
 * @create 2023/6/6
 */
@Getter
@AllArgsConstructor
public enum StorageTypeEnum {
    ALI_OSS("ALI_OSS","阿里云OSS","阿里云服务器"),
    LOCAL_STORAGE("LOCAL_STORAGE","本地存储","本地服务器");
    private String code;
    private String msg;
    private String location;
}
