package com.shop.tsshop.web.model.dto;

import lombok.Data;

@Data
public class OrderListDto {

    private Integer orderStatus;

    private Integer pageNumber;

    private Integer pageSize;

    private Long userId;
}
