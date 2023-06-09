package com.shop.tsshop.web.model.domain.pay;

import com.alipay.api.AlipayClient;
import lombok.Data;


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

    private AlipayClient alipayClient;

}
