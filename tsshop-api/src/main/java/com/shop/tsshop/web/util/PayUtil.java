package com.shop.tsshop.web.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.shop.tsshop.web.model.domain.PayThoroughfare;
import com.shop.tsshop.web.model.domain.pay.PayTypeVo;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : tsshop
 * @date : 2023/4/23
 */
public class PayUtil {

    public static ConcurrentHashMap<Long, PayThoroughfare> payThoroughfareMap;

    public static List<PayTypeVo> payTypeVos;

    public static Long updateTime = 0L;

    /**
     *
     * @param appId
     * @param merchantPrivateKey
     * @param merchantCertPath
     * @param alipayCertPath
     * @param alipayRootCertPath
     * @return
     * @throws AlipayApiException
     */
    public static AlipayClient getAliPay(String appId,String merchantPrivateKey,String merchantCertPath,String alipayCertPath,String alipayRootCertPath) throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        //设置网关地址
        alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
        //设置应用ID
        alipayConfig.setAppId(appId);
        //设置应用私钥
        alipayConfig.setPrivateKey(merchantPrivateKey);
        //设置请求格式，固定值json
        alipayConfig.setFormat("JSON");
        //设置字符集
        alipayConfig.setCharset("UTF-8");
        //设置签名类型
        alipayConfig.setSignType("RSA2");
        //设置应用公钥证书路径
        alipayConfig.setAppCertPath(merchantCertPath);
        //设置支付宝公钥证书路径
        alipayConfig.setAlipayPublicCertPath(alipayCertPath);
        //设置支付宝根证书路径
        alipayConfig.setRootCertPath(alipayRootCertPath);
        //构造client
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        return alipayClient;
    }
}
