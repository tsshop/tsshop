package com.shop.tsshop.web.model.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Integer pageNumber;
    private Integer pageSize;
    /**
     *类别 0：未评价 ，1：评价
     */
    private Integer type;
    private Long userId;
}
