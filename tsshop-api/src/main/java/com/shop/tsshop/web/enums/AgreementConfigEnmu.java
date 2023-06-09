package com.shop.tsshop.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName AgreementConfigEnmu
 * @Author TS SHOP
 * @create 2023/6/9
 */
@Getter
@AllArgsConstructor
public enum AgreementConfigEnmu {
    SERVICE_AGREEMENT("SERVICE_AGREEMENT","服务协议"),
    PRIVACY_AGREEMENT("PRIVACY_AGREEMENT","隐私协议"),
    PAY_AGREEMENT("PAY_AGREEMENT","支付协议");
    private String code;
    private String msg;
}
