package com.ts.shop.domain.pay;

import lombok.Data;

/**
 * @author : my
 * @date : 2023/4/23
 */
@Data
public class AlipayParams {

    //应用ID
    private String appId;

    //应用私钥
    private String merchantPrivateKey;

    //应用公钥证书路径
    private String merchantCertPath;

    //支付宝公钥证书路径
    private String alipayCertPath;

    //支付宝根证书路径
    private String alipayRootCertPath;

    //回调地址
    private String notifyUrl;

//    private AlipayClient alipayClient;

}
