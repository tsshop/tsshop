package com.ts.shop.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName AmountTypeVo
 * @Author TS SHOP
 * @create 2023/6/1
 */
@Data
@Accessors(chain = true)
public class AmountTypeVo {
    /**
     * 支付通道 ID
     */
    private Long payThoroughfareId;
    /**
     * 支付类型
     */
    private String type;
    /**
     * 支付LOGO
     */
    private String logo;
    /**
     * 支付类型名
     */
    private String name;
    /**
     * 选中状态
     */
    private Boolean checkStatus;
}
