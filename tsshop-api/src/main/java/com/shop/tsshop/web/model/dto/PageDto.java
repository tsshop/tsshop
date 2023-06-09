package com.shop.tsshop.web.model.dto;

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
