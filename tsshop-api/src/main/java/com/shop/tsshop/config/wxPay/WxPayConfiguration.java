package com.shop.tsshop.config.wxPay;//package com.shop.tsshop.config.wxPay;
//
//import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
//import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//
//import java.io.IOException;
//import java.io.InputStream;
//@Configuration
//@ConditionalOnClass(CloseableHttpClient.class)
////@AllArgsConstructor
//public class WxPayConfiguration {
//
//    @Value("${wx.pay.mchId}")
//    private String mchid;
//    @Value("${wx.pay.serialNo}")
//    private String serialNo;
//    @Value("${wx.pay.keyPath}")
//    private String keyPath;
//
//    @Bean
//    @ConditionalOnMissingBean
//    public CloseableHttpClient wxService() throws IOException {
//
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources = resolver.getResources(keyPath);
//        Resource resource = resources[0];
//        InputStream is = resource.getInputStream();
//
//        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
//                .withMerchant(mchid, serialNo, PemUtil.loadPrivateKey(is));
//        builder.withValidator(response -> true);
//        CloseableHttpClient httpClient = builder.build();
//        return httpClient;
//    }
//}
