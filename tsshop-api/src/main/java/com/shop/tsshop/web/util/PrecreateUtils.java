package com.shop.tsshop.web.util;

import cn.hutool.log.Log;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName PrecreateUtils
 * @Author TS SHOP
 * @create 2023/5/22
 */
@Data
@Slf4j
public class PrecreateUtils {
    /**
     * 发送请求
     *
     * @param url    eg:https://test-api-open.chinaums.com/v2/poslink/transaction/pay
     * @param entity eg:{"merchantCode":"123456789900081","terminalCode":"00810001","transactionAmount":1,"transactionCurrencyCode":"156","merchantOrderId":"201905211550526234902343","merchantRemark":"商户备注","payMode":"CODE_SCAN","payCode":"285059979526414634"}
     * @return
     * @throws Exception
     */
    public static String send(String url, String entity , String authorization) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", authorization);
        StringEntity se = new StringEntity(entity, "UTF-8");
        se.setContentType("application/json");
        httpPost.setEntity(se);
        log.info("请求报文信息" + entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity1 = response.getEntity();
        String resStr = null;
        if (entity1 != null) {
            resStr = EntityUtils.toString(entity1, "UTF-8");
        }
        httpClient.close();
        response.close();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>" + resStr);
        return resStr;
    }

    /**
     * open-body-sig方式获取到Authorization 的值
     *
     * @param appId  f0ec96ad2c3848b5b810e7aadf369e2f
     * @param appKey 775481e2556e4564985f5439a5e6a277
     * @param body   json字符串 String body = "{\"merchantCode\":\"123456789900081\",\"terminalCode\":\"00810001\",\"merchantOrderId\":\"20123333644493200\",\"transactionAmount\":\"1\",\"merchantRemark\":\"测试\",\"payMode\":\"CODE_SCAN\",\"payCode\":\"285668667587422761\",\"transactionCurrencyCode\":\"156\"}";
     * @return
     * @throws Exception
     */
    public static String getOpenBodySig(String appId, String appKey, String body) throws Exception {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String nonce = UUID.randomUUID().toString().replace("-", "");
        byte[] data = body.getBytes("UTF-8");
        System.out.println("data:\n" + body);
        InputStream is = new ByteArrayInputStream(data);
        String bodyDigest = DigestUtils.sha256Hex(is);
        System.out.println("bodyDigest:\n" + bodyDigest);
        String str1_C = appId + timestamp + nonce + bodyDigest;

        System.out.println("str1_C:" + str1_C);
        byte[] localSignature = hmacSHA256(str1_C.getBytes(), appKey.getBytes());

        String localSignatureStr = Base64.encodeBase64String(localSignature);   // Signature
        System.out.println("Authorization:\n" + "OPEN-BODY-SIG AppId=" + "\"" + appId + "\"" + ", Timestamp=" + "\"" + timestamp + "\"" + ", Nonce=" + "\"" + nonce + "\"" + ", Signature=" + "\"" + localSignatureStr + "\"\n");
        return ("OPEN-BODY-SIG AppId=" + "\"" + appId + "\"" + ", Timestamp=" + "\"" + timestamp + "\"" + ", Nonce=" + "\"" + nonce + "\"" + ", Signature=" + "\"" + localSignatureStr + "\"");
    }
    /**
     * @param data
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] hmacSHA256(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        String algorithm = "HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data);
    }


    public static String makeSign(String md5Key, Map<String, String> params) {

        String preStr = buildSignString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String text = preStr + md5Key;
        System.out.println("待签名字符串：" + text);
        return DigestUtils.sha256Hex(getMessageBytes(text)).toUpperCase();
    }


    public static String buildSignString(Map<String, String> params) {

        if (params == null || params.size() == 0) {
            return "";
        }

        List<String> keys = new ArrayList<>(params.size());

        for (String key : params.keySet()) {
            if ("sign".equals(key))
                continue;
            if (StringUtils.isEmpty(params.get(key)))
                continue;
            keys.add(key);
        }

        Collections.sort(keys);

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                buf.append(key + "=" + value);
            } else {
                buf.append(key + "=" + value + "&");
            }
        }

        return buf.toString();
    }

    public static JSONObject doPost(String url, JSONObject json) {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            CloseableHttpResponse res = httpclient.execute(post);
            HttpEntity entity = res.getEntity();
            String result = EntityUtils.toString(entity);// 返回json格式：
            response = JSONObject.parseObject(result);
            res.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    public static byte[] getMessageBytes(String message) {
        try {
            return message.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误");
        }
    }

    public static Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        Map<String, String> params2 = new HashMap<>();
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            if (values.length > 0) {
                params2.put(key, request.getParameter(key));
            }
        }
        return params2;
    }
}
