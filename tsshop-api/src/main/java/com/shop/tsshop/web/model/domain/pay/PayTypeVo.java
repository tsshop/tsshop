package com.shop.tsshop.web.model.domain.pay;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName PayTypeVo
 * @Author TS SHOP
 * @create 2023/5/19
 */
@Data
@Accessors(chain = true)
public class PayTypeVo {
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
}
