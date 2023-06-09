package com.shop.tsshop.config.wxPay;

import com.alibaba.fastjson.JSONObject;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

@Component
public class WxAPIV3AesUtil {

    /**
     * 对称解密
     *
     * @param bodyMap
     * @return
     */
    public static String decryptFromResource(JSONObject json, String apiV3Key)
            throws GeneralSecurityException {

        // 通知数据
        JSONObject resource = json.getJSONObject("resource");
        // 数据密文
        String ciphertext = resource.getString("ciphertext");
        // 随机串
        String nonce = resource.getString("nonce");
        // 附加数据
        String associatedData = resource.getString("associated_data");

        AesUtil aesUtil = new AesUtil(apiV3Key.getBytes(StandardCharsets.UTF_8));
        String plainText = aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8),
                nonce.getBytes(StandardCharsets.UTF_8), ciphertext);

        return plainText;
    }


}
