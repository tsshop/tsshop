package com.shop.tsshop.web.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private List<Long> ids;
    private Long userId;
    private Long addressId;
    private Integer isPass;
}
