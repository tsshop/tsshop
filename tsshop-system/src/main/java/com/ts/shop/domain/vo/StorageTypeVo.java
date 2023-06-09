package com.ts.shop.domain.vo;

import lombok.Data;

/**
 * @ClassName StorageTypeVo
 * @Author TS SHOP
 * @create 2023/6/6
 */
@Data
public class StorageTypeVo {
    /**
     * 类型标识
     */
    private String code;
    /**
     * 中文名
     */
    private String msg;
    /**
     * 存储地址
     */
    private String location;
}
