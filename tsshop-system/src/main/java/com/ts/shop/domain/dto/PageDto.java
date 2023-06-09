package com.ts.shop.domain.dto;

import lombok.Data;

/**
 * @ClassName PageDto
 * @Author TS SHOP
 * @create 2023/5/25
 */
@Data
public class PageDto {
    private Integer pageNumber;
    private Integer pageSize;
    private String keyword;
}
