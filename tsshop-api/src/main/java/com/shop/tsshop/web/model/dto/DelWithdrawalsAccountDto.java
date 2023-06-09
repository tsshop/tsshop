package com.shop.tsshop.web.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName DelWithdrawalsAccountDto
 * @Author TS SHOP
 * @create 2023/6/1
 */
@Data
public class DelWithdrawalsAccountDto {
    /**
     * 删除id 集合
     */
    private List<Long> ids;
}
