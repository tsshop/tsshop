package com.ts.shop.domain.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : my
 * @date : 2023/3/30
 */
public class PayEnums {

    @Getter
    @AllArgsConstructor
    public enum PayTypeEnum{
        ALI_PAY_APP("ALI_PAY_APP","支付宝APP支付"),

        UNION_PAY_APP("UNION_PAY_APP","云闪付APP支付"),
        WEI_XIN_PAY_LITE("WEI_XIN_PAY_LITE","微信小程序支付"),

        WEI_XIN_PAY_APP("WEI_XIN_PAY_APP","微信APP支付"),
        ALI_PAY_H5("ALI_PAY_H5","支付宝H5支付"),

        ALI_PAY_LITE("ALI_PAY_LITE","支付宝小程序支付"),
        APPLE_PAY("APPLE_PAY","苹果支付");
        private String code;
        private String msg;
    }


    @Getter
    @AllArgsConstructor
    public enum ProviderEnum{
        ALI_PAY("ALI_PAY","支付宝官方"),
        UNION_PAY("UNION_PAY","银联"),

        WE_CHAT_PAY("WE_CHAT_PAY","微信官方");
        private String code;
        private String msg;
    }
}
