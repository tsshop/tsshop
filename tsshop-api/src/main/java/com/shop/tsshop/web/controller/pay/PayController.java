package com.shop.tsshop.web.controller.pay;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.tsshop.common.utils.StringUtils;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.config.wxPay.WxAPIV3AesUtil;
import com.shop.tsshop.web.enums.NumberEnmus;
import com.shop.tsshop.web.enums.PayEnums;
import com.shop.tsshop.web.enums.StorageTypeEnum;
import com.shop.tsshop.web.mapper.PayLogMapper;
import com.shop.tsshop.web.mapper.PayThoroughfareMapper;
import com.shop.tsshop.web.model.domain.*;
import com.shop.tsshop.web.model.domain.pay.*;
import com.shop.tsshop.web.model.dto.CartDto;
import com.shop.tsshop.web.model.dto.OrderDto;
import com.shop.tsshop.web.model.dto.OrderPayDto;
import com.shop.tsshop.web.service.*;
import com.shop.tsshop.web.util.LocalStorageUtils;
import com.shop.tsshop.web.util.OssUtil;
import com.shop.tsshop.web.util.PayUtil;
import com.shop.tsshop.web.util.PrecreateUtils;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName PayController
 * @Author TS SHOP
 * @create 2023/5/18
 */
@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;


    @Autowired
    GoodsService goodsService;

    @Autowired
    GoodsSkuService skuService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    PayLogMapper payLogMapper;

    @Autowired
    PayLogService payLogService;

    @Autowired
    private PayThoroughfareService payThoroughfareService;

    @Autowired
    private LocalStorageUtils localStorageUtils;

    @Autowired
    private OssUtil ossUtil;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserLiveGiftService userLiveGiftService;


    @GetMapping("/getPayTypeList")
    @PassToken
    public ApiResult getPayTypeList(String environmentVariable){
        Object obj = redisService.getObj("pay:updateTime");
        if (ObjectUtil.isNotEmpty(obj)){
            Long updateTime = (Long) obj;
            if (!PayUtil.updateTime.equals(updateTime)){
                payThoroughfareService.initializationParams();
                PayUtil.updateTime = updateTime;
            }
        }
        List<PayTypeVo> payTypeVos = new ArrayList<>();
        if ("MP_WEIXIN".equals(environmentVariable)){
            for (PayTypeVo payTypeVo : PayUtil.payTypeVos) {
                if (payTypeVo.getType().equals(PayEnums.PayTypeEnum.WEI_XIN_PAY_LITE.getCode()) &&
                        Objects.equals(PayUtil.payThoroughfareMap.get(payTypeVo.getPayThoroughfareId()).getPayProvider(),
                                PayEnums.ProviderEnum.WE_CHAT_PAY.getCode())){
                    payTypeVos.add(payTypeVo);
                }
            }
        }else if("H5".equals(environmentVariable)){
            for (PayTypeVo payTypeVo : PayUtil.payTypeVos) {
                if (payTypeVo.getType().equals(PayEnums.PayTypeEnum.WEI_XIN_PAY_LITE.getCode()) || payTypeVo.getType()
                        .equals(PayEnums.PayTypeEnum.ALI_PAY_H5.getCode())){
                    payTypeVos.add(payTypeVo);
                }
            }
        }else {
            payTypeVos = PayUtil.payTypeVos;
        }
        return ApiResult.ok(payTypeVos);
    }





    @PostMapping
    @PassToken
    @Transactional(rollbackFor = Exception.class)
    public ApiResult pay(@RequestBody OrderPayDto dto, HttpServletRequest request) throws Exception {
        log.info(dto.toString());
        // ==============================     校验支付方式    ================================
        PayThoroughfare payThoroughfare = PayUtil.payThoroughfareMap.get(dto.getPayThoroughfareId());
        if (ObjectUtil.isEmpty(payThoroughfare)){
            return ApiResult.fail("支付方式不存在，请刷新后重试");
        }
        Long aLong = dto.getOrderId().stream().findFirst().get();
        //==================================================================================
        //>>>>>>>>>>>>>>>>                       校验订单信息
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String orderNo;
        if (PayEnums.ProviderEnum.UNION_PAY.getCode().equals(payThoroughfare.getPayProvider())){
            orderNo = payThoroughfare.getUnionPayParams().getOrderPrefix() + snowflake.nextId() + "";
        }else {
            orderNo = snowflake.nextId() + "";
        }
        PayLog payLog = new PayLog();
        payLog.setOrderNoPay(orderNo);
        payLog.setPayThoroughfareId(dto.getPayThoroughfareId());
        payLog.setPayType(dto.getPayType());
        payLogMapper.insert(payLog);
        // 总价
        BigDecimal amt = new BigDecimal("0");
        for (Long id : dto.getOrderId()) {
            Order byId = orderService.getById(id);
            if (byId == null || byId.getOrderStatus()!=0){
                throw new MyException(500,"请刷新后重试");
            }
            amt = amt.add(byId.getTotalAmount());
            byId.setPayThoroughfareId(dto.getPayThoroughfareId());
            byId.setPayType(dto.getPayType());
            byId.setPayLogId(payLog.getId());
            orderService.updateById(byId);
        }

        int total = amt.multiply(new BigDecimal("100")).intValue();
        //===================================================================================
        if (PayEnums.ProviderEnum.WE_CHAT_PAY.getCode().equals(payThoroughfare.getPayProvider())){
            return weChatPay(orderNo,total,dto,request);
        }else if (PayEnums.ProviderEnum.UNION_PAY.getCode().equals(payThoroughfare.getPayProvider())){
            return unionPay(orderNo,total,dto,request);
        }else if (PayEnums.ProviderEnum.ALI_PAY.getCode().equals(payThoroughfare.getPayProvider())){
            return aliPay(orderNo,amt,dto,payThoroughfare);
        }
        return ApiResult.fail("请选择正确的支付方式");
    }
    /**
     * 支付宝支付
     * @param orderNo       商家订单号
     * @param amt           金额
     * @param dto           dto
     * @return              统一返回
     */
    private ApiResult aliPay(String orderNo, BigDecimal amt, OrderPayDto dto, PayThoroughfare payThoroughfare) throws AlipayApiException {
        if (!PayEnums.PayTypeEnum.ALI_PAY_APP.getCode().equals(dto.getPayType())){
            return ApiResult.fail("无效的支付方式");
        }
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo);
        bizContent.put("total_amount", amt + "");
        String notifyUrl = payThoroughfare.getAlipayParams().getNotifyUrl();
        log.info("支付宝回调地址：" + notifyUrl + "/pay/aliPay/notifyUrl");
        request.setNotifyUrl(notifyUrl + "/pay/aliPay/notifyUrl");
        bizContent.put("subject","商品购买");

        request.setBizContent(bizContent.toString());
        AlipayTradeAppPayResponse response = payThoroughfare.getAlipayParams().getAlipayClient().sdkExecute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            System.out.println("response:" + response.getBody());
            return ApiResult.ok(response.getBody());
        } else {
            System.out.println("调用失败，原因：" + response.getBody());
            return ApiResult.fail("支付失败");
        }
    }




    /**
     * 银联支付
     * @param orderNo      订单号
     * @param total        金额
     * @param dto          请求参数
     * @return             统一返回
     */
    private ApiResult unionPay(String orderNo, int total, OrderPayDto dto,HttpServletRequest request) throws Exception {

        String agent = request.getHeader("User-Agent");
        PayThoroughfare payThoroughfare = PayUtil.payThoroughfareMap.get(dto.getPayThoroughfareId());
        UnionPayParams unionPayParams = payThoroughfare.getUnionPayParams();
        log.info("通道参数 [{}]" + unionPayParams);
        JSONObject json = new JSONObject();
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));    // 报文请求时间
        json.put("merOrderId",orderNo); // 商户订单号
        json.put("mid", unionPayParams.getMid()); // 商户号
        json.put("tid", unionPayParams.getTid());    // 终端号

        json.put("totalAmount",total);      // 支付总金额
        json.put("subAppId",unionPayParams.getSubAppId());
        json.put("notifyUrl",unionPayParams.getNotifyUrl() + "/pay/union/notifyUrl");
        String url = null;
        if (PayEnums.PayTypeEnum.WEI_XIN_PAY_LITE.getCode().equals(dto.getPayType())){
            url = unionPayParams.getWeChatLiteUrl();
            json.put("tradeType","MINI");      // 支付总金额
            json.put("instMid", "MINIDEFAULT"); // 业务类型
            json.put("subOpenId",getOpenId(dto.getCode(),unionPayParams.getSubAppId(),unionPayParams.getWeChatLiteSecret()));
        }
        if (PayEnums.PayTypeEnum.ALI_PAY_H5.getCode().equals(dto.getPayType())){
            json.put("instMid", "APPDEFAULT"); // 业务类型
            url = unionPayParams.getAliPayH5Url();
        }
        if (PayEnums.PayTypeEnum.ALI_PAY_LITE.getCode().equals(dto.getPayType())){
            json.put("instMid", "APPDEFAULT"); // 业务类型
            url = unionPayParams.getAliPayLiteUrl();
            if(agent.contains("Android")){//安卓手机
                json.put("targetAppScheme","Android:" + unionPayParams.getSchemeUrl());
            }else if(agent.contains("iPhone")||agent.contains("iPod")||agent.contains("iPad")){//苹果手机
                json.put("targetAppScheme","iOS:" + unionPayParams.getSchemeUrl());
            }
        }
        String authorization = PrecreateUtils.getOpenBodySig(unionPayParams.getAppId(), unionPayParams.getAppKey(), json.toString());
        String send = PrecreateUtils.send(url,json.toString(),authorization);

        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(send);
        if (!"SUCCESS".equals(jsonObject.getStr("errCode"))){
            return ApiResult.fail("请刷新后重试");
        }
        if (PayEnums.PayTypeEnum.WEI_XIN_PAY_LITE.getCode().equals(dto.getPayType())){
            return ApiResult.ok(jsonObject.getJSONObject("miniPayRequest"));
        }
        return ApiResult.ok(jsonObject);

    }
    /**
     * 微信支付
     * @param orderNo        交易订单号
     * @param total          总计金额 分
     * @param dto            请求参数 订单信息
     * @return               统一返回
     */
    private ApiResult weChatPay(String orderNo,int total,OrderPayDto dto,HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        //  微信 APP 支付
        if (PayEnums.PayTypeEnum.WEI_XIN_PAY_APP.getCode().equals(dto.getPayType())){
            return weChatAppPay(orderNo,total,dto);
        }
        //  微信小程序支付
        if (PayEnums.PayTypeEnum.WEI_XIN_PAY_LITE.getCode().equals(dto.getPayType())){
            return weChatLitePay(orderNo,total,dto,request);
        }
        return ApiResult.fail("无效的支付方式");

    }

    /**
     * 微信小程序支付
     * @param orderNo             支付单号
     * @param total               总计金额 分
     * @param dto                 请求参数
     * @return
     */
    private ApiResult weChatLitePay(String orderNo, int total, OrderPayDto dto,HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        PayThoroughfare payThoroughfare = PayUtil.payThoroughfareMap.get(dto.getPayThoroughfareId());
        WeChatAppParams weChatAppParams = payThoroughfare.getWeChatAppParams();
        // 获取私钥文件路径
        TsshopConfigStorage config = getConfig();
        if (ObjectUtil.isNull(config)){
            throw new RuntimeException("存储配置信息有误请，前往后台配置");
        }
        String absolutePath = null;
        if (StorageTypeEnum.ALI_OSS.getCode().equals(config.getStorageType())){
            absolutePath = ossUtil.getCertFile(config,weChatAppParams.getKeyPath()).getAbsolutePath();
        }else if (StorageTypeEnum.LOCAL_STORAGE.getCode().equals(config.getStorageType())){
            absolutePath = localStorageUtils.getCertFile(weChatAppParams.getKeyPath()).getAbsolutePath();
        }
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        JSONObject req=new JSONObject();
        req.put("appid", weChatAppParams.getAppid());//服务商申请的公众号或移动应用appid
        req.put("mchid", weChatAppParams.getMchid());//服务商户号，由微信支付生成并下发
        req.put("description","商城商品");//商品描述
        req.put("out_trade_no", orderNo);// 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一，详见【商户订单号】
        req.put("notify_url", weChatAppParams.getNotify_url() + "/pay/notifyUrl");
        JSONObject amount=new JSONObject();
        amount.put("total",total);
        amount.put("currency","CNY");
        req.put("amount", amount);
        JSONObject payer=new JSONObject();
        User currentUser = redisService.getCurrentUser(request);
        User byId = userService.getById(currentUser.getId());
        payer.put("openid",byId.getOpenid());
        req.put("payer", payer);
        httpPost.setEntity(new StringEntity(req.toString(), "UTF-8"));


        TsshopConfigStorage wxConfig = getConfig();
        if (ObjectUtil.isNull(wxConfig)){
            throw new RuntimeException("存储配置信息有误请，前往后台配置");
        }
        String keyPath = null;
        if (StorageTypeEnum.ALI_OSS.getCode().equals(config.getStorageType())){
            keyPath = ossUtil.getCertFile(config,weChatAppParams.getKeyPath()).getAbsolutePath();
        }else if (StorageTypeEnum.LOCAL_STORAGE.getCode().equals(config.getStorageType())){
            keyPath = localStorageUtils.getCertFile(weChatAppParams.getKeyPath()).getAbsolutePath();
        }
        if (StringUtils.isEmpty(keyPath)){
            throw new RuntimeException("获取支付参数错误");
        }
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(keyPath);
        Resource resource = resources[0];
        InputStream is = resource.getInputStream();

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(weChatAppParams.getMchid(), weChatAppParams.getSerialNo(), PemUtil.loadPrivateKey(is));
        builder.withValidator(response -> true);
        CloseableHttpClient httpClient = builder.build();

        CloseableHttpResponse response = httpClient.execute(httpPost);

        JSONObject bodyAsString = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        log.info(">>>>>>>>>>>>>>>>>>" + bodyAsString);
        String nonceStr = RandomUtil.randomString(32);//随机字符串
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);// 时间戳
        String preId = "prepay_id=" + bodyAsString.getString("prepay_id");

        String paySign = getSign(weChatAppParams.getAppid(), timeStamp, nonceStr, preId, absolutePath);// 签名

        JSONObject data=new JSONObject();
        data.put("timeStamp",timeStamp);
        data.put("nonceStr",nonceStr);
        data.put("paySign",paySign);
        data.put("signType","RSA");
        data.put("package",preId);
        return ApiResult.ok(data);
    }

    private Object getOpenId(String code, String wxAppId, String wxAppSecret) {
        String token = null;
        String params = null;
        if(redisTemplate.hasKey("wx:AccessToken")){
            token = redisService.getObj("wx:AccessToken").toString();
        }else {
            params = "https://api.weixin.qq.com/cgi-bin/token";//网址;
            JSONObject req=new JSONObject();
            req.put("appid", wxAppId);
            req.put("secret", wxAppSecret);
            req.put("grant_type", "client_credential");
            JSONObject json = JSONObject.parseObject(HttpUtil.get(params,req));
            token=json.getString("access_token");
            redisService.saveCode("wx:AccessToken",token, json.getLong("expires_in")-10);
        }

        params = "https://api.weixin.qq.com/sns/jscode2session";//网址;
        JSONObject req=new JSONObject();
        req.put("appid", wxAppId);
        req.put("secret", wxAppSecret);
        req.put("js_code", code);
        req.put("grant_type", "authorization_code");
        JSONObject json = JSONObject.parseObject(HttpUtil.post(params, req));
        System.out.println(json);
        return json.getString("openid");

    }


    /**
     * 微信APP支付
     * @param orderNo             支付单号
     * @param total               总计金额 分
     * @param dto                 请求参数
     * @return
     */
    private ApiResult weChatAppPay(String orderNo, int total, OrderPayDto dto) throws IOException, NoSuchAlgorithmException {
        PayThoroughfare payThoroughfare = PayUtil.payThoroughfareMap.get(dto.getPayThoroughfareId());
        WeChatAppParams weChatAppParams = payThoroughfare.getWeChatAppParams();
        // 获取私钥文件路径
        TsshopConfigStorage config = getConfig();
        if (ObjectUtil.isNull(config)){
            throw new RuntimeException("存储配置信息有误请，前往后台配置");
        }
        String absolutePath = null;
        if (StorageTypeEnum.ALI_OSS.getCode().equals(config.getStorageType())){
            absolutePath = ossUtil.getCertFile(config,weChatAppParams.getKeyPath()).getAbsolutePath();
        }else if (StorageTypeEnum.LOCAL_STORAGE.getCode().equals(config.getStorageType())){
            absolutePath = localStorageUtils.getCertFile(weChatAppParams.getKeyPath()).getAbsolutePath();
        }



        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/app");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");

        JSONObject req=new JSONObject();
        req.put("appid", weChatAppParams.getAppid());//服务商申请的公众号或移动应用appid
        req.put("mchid", weChatAppParams.getMchid());//服务商户号，由微信支付生成并下发
        req.put("description","商城商品");//商品描述
        req.put("out_trade_no", orderNo);// 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一，详见【商户订单号】
        log.info("支付回调地址：[]" + weChatAppParams.getNotify_url() + "/pay/notifyUrl");
        req.put("notify_url", weChatAppParams.getNotify_url() + "/pay/notifyUrl");

        JSONObject amount=new JSONObject();
        amount.put("total",total);
        amount.put("currency","CNY");
        req.put("amount", amount);
        httpPost.setEntity(new StringEntity(req.toString(), "UTF-8"));


        TsshopConfigStorage wxConfig = getConfig();
        if (ObjectUtil.isNull(wxConfig)){
            throw new RuntimeException("存储配置信息有误请，前往后台配置");
        }
        String keyPath = null;
        if (StorageTypeEnum.ALI_OSS.getCode().equals(config.getStorageType())){
            keyPath = ossUtil.getCertFile(config,weChatAppParams.getKeyPath()).getAbsolutePath();
        }else if (StorageTypeEnum.LOCAL_STORAGE.getCode().equals(config.getStorageType())){
            keyPath = localStorageUtils.getCertFile(weChatAppParams.getKeyPath()).getAbsolutePath();
        }
        if (StringUtils.isEmpty(keyPath)){
            throw new RuntimeException("获取支付参数错误");
        }
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(keyPath);
        Resource resource = resources[0];
        InputStream is = resource.getInputStream();

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(weChatAppParams.getMchid(), weChatAppParams.getSerialNo(), PemUtil.loadPrivateKey(is));
        builder.withValidator(response -> true);
        CloseableHttpClient httpClient = builder.build();


        CloseableHttpResponse response = httpClient.execute(httpPost);



        JSONObject bodyAsString = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        String nonceStr = RandomUtil.randomString(32);//随机字符串
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);// 时间戳
        String preId = bodyAsString.getString("prepay_id");

        String paySign = getSign(weChatAppParams.getAppid(), timeStamp, nonceStr, preId, absolutePath);// 签名

        JSONObject data=new JSONObject();
        data.put("appid",weChatAppParams.getAppid());
        data.put("noncestr",nonceStr);
        data.put("package","Sign=WXPay");
        data.put("partnerid",weChatAppParams.getMchid());
        data.put("prepayid",preId);
        data.put("timestamp",timeStamp);
        data.put("sign",paySign);
        return ApiResult.ok(data);
    }

    /**
     * 获取签名
     * @param appid                       应用ID
     * @param timeStamp                   时间戳
     * @param nonceStr                    随机字符串，不长于32位
     * @param prepayId                    预支付交易会话ID
     * @param privateKeyPath              私钥路径
     * @return                            签名
     */
    public String getSign(String appid ,String timeStamp,String nonceStr,String prepayId, String privateKeyPath) throws NoSuchAlgorithmException, FileNotFoundException {
        try {
            String signatureStr = Stream.of(appid, timeStamp, nonceStr, prepayId)
                    .collect(Collectors.joining("\n", "", "\n"));
            Signature sign = Signature.getInstance("SHA256withRSA");

            FileReader fileReader = new FileReader(privateKeyPath);
            String result = fileReader.readString();
            PrivateKey privateKey = PemUtil.loadPrivateKey(result);
            sign.initSign(privateKey);
            sign.update(signatureStr.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/notifyUrl")
    @PassToken
    @ResponseBody
    public Object notifyUrl(HttpServletRequest request) throws GeneralSecurityException {
        log.info("》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》接收到了回调");
        // 处理通知参数
        JSONObject back=new JSONObject();
        back.put("code","SUCCESS");
        back.put("message","");
        String body = "";
        try (InputStream is = request.getInputStream()) {
            body= IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            log.error("read http request failed.", ex);
        }
        PayThoroughfare weChatPayThoroughfare = new PayThoroughfare();
        List<PayThoroughfare> list = payThoroughfareService.list(new LambdaQueryWrapper<PayThoroughfare>().eq(PayThoroughfare::getPayProvider, PayEnums.ProviderEnum.WE_CHAT_PAY).eq(PayThoroughfare::getIsUse,true));
        for (PayThoroughfare payThoroughfare : list) {
            if (payThoroughfare.getPayTypes().contains(PayEnums.PayTypeEnum.WEI_XIN_PAY_LITE.getCode())){
                weChatPayThoroughfare = payThoroughfare;
            }
            if (payThoroughfare.getPayTypes().contains(PayEnums.PayTypeEnum.WEI_XIN_PAY_APP.getCode())){
                weChatPayThoroughfare = payThoroughfare;
            }
        }
        PayThoroughfare payThoroughfare = PayUtil.payThoroughfareMap.get(weChatPayThoroughfare.getId());
        WeChatAppParams weChatAppParams = payThoroughfare.getWeChatAppParams();
        String plainText = WxAPIV3AesUtil.decryptFromResource(JSONObject.parseObject(body), weChatAppParams.getMchKey());
        // 将明文转换成map
        JSONObject resultMap = JSONObject.parseObject(plainText);
        String orderNo = resultMap.getString("out_trade_no");// 商户订单号
        String transactionId = resultMap.getString("transaction_id");// 微信支付订单号
        String timeEnd = resultMap.getString("time_end");//
        String openid = resultMap.getString("openid");// 用户标识
        String tradeState = resultMap.getString("trade_state");// 交易状态
        Integer payerTotal = resultMap.getJSONObject("amount").getInteger("payer_total");
        Double t = 1.0 * payerTotal / 100;
        PayLog payLog = payLogService.getOne(new LambdaQueryWrapper<PayLog>().eq(PayLog::getOrderNoPay,orderNo));
        List<Order> orderList=orderService.list(new LambdaQueryWrapper<Order>().eq(Order::getPayLogId,payLog.getId()));
        payLog.setPayAmount(new BigDecimal(t));
        payLog.setPayTime(new Date());
        payLogMapper.updateById(payLog);
        orderList.forEach(item ->{
            if (Objects.equals(item.getOrderType(), NumberEnmus.ZERO.getNumber()) || Objects.equals(item.getOrderType(), NumberEnmus.THREE.getNumber())){
                item.setOrderStatus(1);
                item.setAmount(payLog.getPayAmount());
                item.setPayTime(payLog.getPayTime());
                orderService.updateById(item);
                orderService.liveOrderProfit(item);
                goodsService.updateQuantity(item.getGoodsId(),item.getQuantity());
            }
            if (Objects.equals(item.getOrderType(), NumberEnmus.ONE.getNumber())){
                UserLiveGift one = userLiveGiftService.getOne(new LambdaQueryWrapper<UserLiveGift>().eq(UserLiveGift::getUserId, item.getUserId()).eq(UserLiveGift::getLiveGiftId, item.getGoodsId()));
                if (ObjectUtil.isNull(one)){
                    UserLiveGift userLiveGift = new UserLiveGift();
                    userLiveGift.setUserId(item.getUserId());
                    userLiveGift.setLiveGiftId(item.getGoodsId());
                    userLiveGift.setLiveGiftNum(Long.valueOf(item.getQuantity()));
                    userLiveGiftService.save(userLiveGift);
                }else {
                    one.setLiveGiftNum(one.getLiveGiftNum() + item.getQuantity());
                    userLiveGiftService.updateById(one);
                }
                item.setOrderStatus(3);
                item.setAmount(payLog.getPayAmount());
                orderService.updateById(item);
            }
        });
        return back;
    }
    @PostMapping("/union/notifyUrl")
    @PassToken
    @ResponseBody
    public String unionNotifyUrl(HttpServletRequest request) throws GeneralSecurityException {
        log.info("》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》union|notifyUrl");
        Map<String, String> params = PrecreateUtils.getRequestParams(request);
        log.info("params:" + params);
        String sign = params.get("sign");
        if (ObjectUtil.isEmpty(params)){
            return "";
        }
        String orderNo=params.get("merOrderId");
        PayLog payLog = payLogService.getOne(new LambdaQueryWrapper<PayLog>().eq(PayLog::getOrderNoPay,orderNo));
        PayThoroughfare payThoroughfare = PayUtil.payThoroughfareMap.get(payLog.getPayThoroughfareId());
        String strSign = PrecreateUtils.makeSign(payThoroughfare.getUnionPayParams().getSecretKey(), params);
        if (!sign.equals(strSign)) {
            log.info("验签未通过");
            return "";
        }
        // 订单总金额
        Long payTotal = Long.valueOf(params.get("totalAmount"));
        Double t = 1.0 * payTotal / 100;
        payLog.setPayAmount(new BigDecimal(t));
        payLog.setPayTime(new Date());
        payLog.setPayNo(params.get("seqId"));
        payLogMapper.updateById(payLog);
        List<Order> list = orderService.list(new LambdaQueryWrapper<Order>().eq(Order::getPayLogId, payLog.getId()));
        list.forEach(item ->{
            if (Objects.equals(item.getOrderType(), NumberEnmus.ZERO.getNumber()) || Objects.equals(item.getOrderType(), NumberEnmus.THREE.getNumber())){
                item.setOrderStatus(1);
                item.setAmount(payLog.getPayAmount());
                item.setPayTime(payLog.getPayTime());
                orderService.updateById(item);
                orderService.liveOrderProfit(item);
                goodsService.updateQuantity(item.getGoodsId(),item.getQuantity());
            }
            if (Objects.equals(item.getOrderType(), NumberEnmus.ONE.getNumber())){
                UserLiveGift one = userLiveGiftService.getOne(new LambdaQueryWrapper<UserLiveGift>().eq(UserLiveGift::getUserId, item.getUserId()).eq(UserLiveGift::getLiveGiftId, item.getGoodsId()));
                if (ObjectUtil.isNull(one)){
                    UserLiveGift userLiveGift = new UserLiveGift();
                    userLiveGift.setUserId(item.getUserId());
                    userLiveGift.setLiveGiftId(item.getGoodsId());
                    userLiveGift.setLiveGiftNum(Long.valueOf(item.getQuantity()));
                    userLiveGiftService.save(userLiveGift);
                }else {
                    one.setLiveGiftNum(one.getLiveGiftNum() + item.getQuantity());
                    userLiveGiftService.updateById(one);
                }
                item.setOrderStatus(3);
                item.setAmount(payLog.getPayAmount());
                orderService.updateById(item);
            }
        });
        return "SUCCESS";
    }

    @PostMapping("/aliPay/notifyUrl")
    @PassToken
    @ResponseBody
    public String aliPayNotifyUrl(HttpServletRequest request) throws AlipayApiException {
        log.info(">>>>>>>>>>>>>>>>>接收到了支付宝的回调");
        Map< String , String > params = new HashMap< String , String >();
        Map requestParams = request.getParameterMap();
        for(Iterator iter = requestParams.keySet().iterator(); iter.hasNext();){
            String name = (String)iter.next();
            String[] values = (String [])requestParams.get(name);
            String valueStr = "";
            for(int i = 0;i < values.length;i ++ ){
                valueStr =  (i==values.length-1)?valueStr + values [i]:valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put (name,valueStr);
        }
        log.info("支付宝回调参数:" + params);
        String payAmt=params.get("receipt_amount");
        String orderNo=params.get("out_trade_no");
        String tradeNo=params.get("trade_no");

        PayLog payLog = payLogMapper.selectOne(new LambdaQueryWrapper<PayLog>().eq(PayLog::getOrderNoPay, orderNo));
        payLog.setPayNo(tradeNo);
        payLog.setPayAmount(new BigDecimal(payAmt));
        payLog.setPayTime(new Date());
        payLogMapper.updateById(payLog);
        List<Order> list = orderService.list(new LambdaQueryWrapper<Order>().eq(Order::getPayLogId, payLog.getId()));
        list.forEach(item ->{
            if (Objects.equals(item.getOrderType(), NumberEnmus.ZERO.getNumber()) || Objects.equals(item.getOrderType(), NumberEnmus.THREE.getNumber())){
                item.setOrderStatus(1);
                item.setPayTime(payLog.getPayTime());
                item.setAmount(new BigDecimal(payAmt));
                orderService.liveOrderProfit(item);
                orderService.updateById(item);
                goodsService.updateQuantity(item.getGoodsId(),item.getQuantity());
            }
            if (Objects.equals(item.getOrderType(), NumberEnmus.ONE.getNumber())){
                UserLiveGift one = userLiveGiftService.getOne(new LambdaQueryWrapper<UserLiveGift>().eq(UserLiveGift::getUserId, item.getUserId()).eq(UserLiveGift::getLiveGiftId, item.getGoodsId()));
                if (ObjectUtil.isNull(one)){
                    UserLiveGift userLiveGift = new UserLiveGift();
                    userLiveGift.setUserId(item.getUserId());
                    userLiveGift.setLiveGiftId(item.getGoodsId());
                    userLiveGift.setLiveGiftNum(Long.valueOf(item.getQuantity()));
                    userLiveGiftService.save(userLiveGift);
                }else {
                    one.setLiveGiftNum(one.getLiveGiftNum() + item.getQuantity());
                    userLiveGiftService.updateById(one);
                }
                item.setOrderStatus(3);
                item.setAmount(new BigDecimal(payAmt));
                orderService.updateById(item);
            }

        });
        return "success";
    }


    /**
     * 获取配置信息
     * @return                 存储配置
     */
    public TsshopConfigStorage getConfig(){
        String config = (String) redisTemplate.opsForValue().get("config:storage");
        if (ObjectUtil.isNull(config)){
            throw new RuntimeException("存储配置信息有误请，前往后台配置");
        }
        return JSONUtil.toBean(config, TsshopConfigStorage.class);
    }
}
