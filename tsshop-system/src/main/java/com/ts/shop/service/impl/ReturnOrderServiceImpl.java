package com.ts.shop.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.exception.ServiceException;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.*;
import com.ts.shop.domain.param.ReturnParam;
import com.ts.shop.domain.pay.AlipayParams;
import com.ts.shop.domain.pay.UnionPayParams;
import com.ts.shop.domain.pay.WeChatAppParams;
import com.ts.shop.domain.pay.PayEnums;
import com.ts.shop.enmus.StorageTypeEnum;
import com.ts.shop.mapper.*;
import com.ts.shop.service.IPayThoroughfareService;
import com.ts.shop.util.LocalStorageUtils;
import com.ts.shop.util.OssUtil;
import com.ts.shop.util.PayUtil;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.ts.shop.service.IReturnOrderService;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 退款Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
@Slf4j
public class ReturnOrderServiceImpl implements IReturnOrderService 
{
    @Autowired
    private ReturnOrderMapper returnOrderMapper;

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IPayThoroughfareService payThoroughfareService;

    @Autowired
    private PayLogMapper payLogMapper;

    @Autowired
    private ReturnPayLogMapper returnPayLogMapper;
    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private OssUtil myOSS;

    @Autowired
    private LocalStorageUtils localStorageUtils;
    /**
     * 查询退款
     * 
     * @param id 退款主键
     * @return 退款
     */
    @Override
    public ReturnOrder selectReturnOrderById(Long id)
    {
        return returnOrderMapper.selectReturnOrderById(id);
    }

    /**
     * 查询退款列表
     * 
     * @param returnOrder 退款
     * @return 退款
     */
    @Override
    public List<ReturnOrder> selectReturnOrderList(ReturnOrder returnOrder)
    {
        return returnOrderMapper.selectReturnOrderList(returnOrder);
    }

    /**
     * 新增退款
     * 
     * @param returnOrder 退款
     * @return 结果
     */
    @Override
    public int insertReturnOrder(ReturnOrder returnOrder)
    {
        returnOrder.setCreateTime(DateUtils.getNowDate());
        return returnOrderMapper.insertReturnOrder(returnOrder);
    }

    /**
     * 修改退款
     * 
     * @param returnOrder 退款
     * @return 结果
     */
    @Override
    public int updateReturnOrder(ReturnOrder returnOrder)
    {
        returnOrder.setUpdateTime(DateUtils.getNowDate());
        return returnOrderMapper.updateReturnOrder(returnOrder);
    }

    /**
     * 批量删除退款
     * 
     * @param ids 需要删除的退款主键
     * @return 结果
     */
    @Override
    public int deleteReturnOrderByIds(Long[] ids)
    {
        return returnOrderMapper.deleteReturnOrderByIds(ids);
    }

    /**
     * 删除退款信息
     * 
     * @param id 退款主键
     * @return 结果
     */
    @Override
    public int deleteReturnOrderById(Long id)
    {
        return returnOrderMapper.deleteReturnOrderById(id);
    }


    @Override
    public Object getGoods(Long id) {
        ReturnOrder returnOrder=returnOrderMapper.selectReturnOrderById(id);
        if(returnOrder==null){
            throw new ServiceException("订单不存在");
        }
        Order order =orderMapper.selectByOrderNo(returnOrder.getOrderNo());
        Goods goods = goodsMapper.selectGoodsById(order.getGoodsId());
        Long skuId = order.getSkuId();
        JSONObject json=new JSONObject();
        if(skuId !=null && skuId !=0){
            GoodsSku sku=goodsSkuMapper.selectGoodsSkuBySkuId(skuId);
            json.put("sku",sku);
        }
        json.put("order",order);
        json.put("goods",goods);
        return json;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void examine(ReturnParam param) throws Exception {
        int status=param.getStatus();
        Long id=param.getId();
        ReturnOrder returnOrder=returnOrderMapper.selectReturnOrderById(id);
        if(returnOrder==null){
            throw new ServiceException("订单不存在");
        }
        int type=returnOrder.getReturnType();
        int rStatus=returnOrder.getStatus();
//        status==1 成功
//        status==2 拒绝
        if((type==1 || type==2) && rStatus==1 && status==1){
            //仅退款，成功退款，需填写金额
            Double reallyAmt = param.getReallyAmt();
            refund(reallyAmt,returnOrder.getOrderNo(),id);
            return;
        }
        if((rStatus==1 || rStatus==5)&& status==2){
            //拒绝退款
            ReturnOrder rUpdate=new ReturnOrder();
            rUpdate.setId(id);
            rUpdate.setStatus(2);
            rUpdate.setRefuseDetail(param.getRefuseDetail());
            returnOrderMapper.updateReturnOrder(rUpdate);
            return;
        }
        if(type==3 && rStatus==1 && status==1){
            //预审核同意，需填写收获地址
            ReturnOrder rUpdate=new ReturnOrder();
            rUpdate.setId(id);
            rUpdate.setStatus(4);
            rUpdate.setAddressId(param.getAddressId());
            returnOrderMapper.updateReturnOrder(rUpdate);
            return;
        }
        if(type==3 && rStatus==5 && status ==1){
            //最终审核通过，成功退款，需填写金额
            Double reallyAmt = param.getReallyAmt();
            refund(reallyAmt,returnOrder.getOrderNo(),id);
            return;
        }
        throw new ServiceException("状态有误");
    }

    @Override
    public Integer getMonthly(int status) {
        return returnOrderMapper.getMonthly(status);
    }

    public int refund(Double amt,String orderNo,Long id) throws Exception {
        Order order = orderMapper.selectByOrderNo(orderNo);
        PayLog payLog = payLogMapper.selectOne(new LambdaQueryWrapper<PayLog>().eq(PayLog::getId,order.getPayLogId()));
        log.info("订单：[{}]",order);
        if(order == null || order.getDeleted() == -1 || (order.getOrderStatus() != 1 && order.getOrderStatus() != 2 && order.getOrderStatus() != 3)){
            throw new ServiceException("订单错误");
        }
        if(amt == null || amt > order.getTotalAmount().doubleValue() || amt <= 0){
            throw new ServiceException("退款金额错误");
        }
        PayThoroughfare payThoroughfare = payThoroughfareService.selectPayThoroughfareById(payLog.getPayThoroughfareId());
        log.info("支付通道：[{}]",payThoroughfare);
        if (ObjectUtil.isEmpty(payThoroughfare) || !payThoroughfare.getIsUse()){
            throw new ServiceException("该订单所在的支付通道不存在或未启用");
        }

        if (PayEnums.ProviderEnum.WE_CHAT_PAY.getCode().equals(payThoroughfare.getPayProvider())){
            weChatAppRefund(order,amt,payThoroughfare,id);
        } else if (PayEnums.ProviderEnum.UNION_PAY.getCode().equals(payThoroughfare.getPayProvider())){
            unionPayRefund(payLog,amt,payThoroughfare,id);
        } else if (PayEnums.ProviderEnum.ALI_PAY.getCode().equals(payThoroughfare.getPayProvider())){
            aliPayRefund(payLog,amt,payThoroughfare,id);
        }
        return 0;
    }

    /**
     * Ali 退款
     * @param payLog              支付记录
     * @param amt                 amt
     * @param payThoroughfare     支付通道
     * @param id                  退款单 ID
     */
    private void aliPayRefund(PayLog payLog, Double amt, PayThoroughfare payThoroughfare, Long id) throws AlipayApiException {
        ReturnOrder returnOrder = returnOrderMapper.selectReturnOrderById(id);
        AlipayParams alipayParams = JSONUtil.toBean(payThoroughfare.getConfig(), AlipayParams.class);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        com.alibaba.fastjson.JSONObject bizContent = new com.alibaba.fastjson.JSONObject();
        bizContent.put("trade_no", payLog.getPayNo());
        bizContent.put("refund_amount", new BigDecimal(amt).setScale(2, RoundingMode.DOWN));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String tkNo=sdf.format(new Date()) + (int) ((Math.random() * 9 + 1) * 1000)+"tk";
        bizContent.put("out_request_no",tkNo);
        AlipayClient alipayClient = null;
        TsshopConfigStorage config = (TsshopConfigStorage) redisTemplate.opsForValue().get("config:storage");
        if (ObjectUtil.isNull(config)){
            throw new RuntimeException("存储配置信息有误请，前往后台配置");
        }
        if (StorageTypeEnum.ALI_OSS.getCode().equals(config.getStorageType())){
            alipayClient = PayUtil.getAliPay(alipayParams.getAppId(),alipayParams.getMerchantPrivateKey(),
                    myOSS.getCertFile(config,alipayParams.getMerchantCertPath()).getAbsolutePath(),
                    myOSS.getCertFile(config,alipayParams.getAlipayCertPath()).getAbsolutePath(),
                    myOSS.getCertFile(config,alipayParams.getAlipayRootCertPath()).getAbsolutePath());
        }else if (StorageTypeEnum.LOCAL_STORAGE.getCode().equals(config.getStorageType())){
            alipayClient = PayUtil.getAliPay(alipayParams.getAppId(),alipayParams.getMerchantPrivateKey(),
                    localStorageUtils.getCertFile(alipayParams.getMerchantCertPath()).getAbsolutePath(),
                    localStorageUtils.getCertFile(alipayParams.getAlipayCertPath()).getAbsolutePath(),
                    localStorageUtils.getCertFile(alipayParams.getAlipayRootCertPath()).getAbsolutePath());
        }

        request.setBizContent(bizContent.toString());
        AlipayTradeRefundResponse response = alipayClient.certificateExecute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            returnOrder.setStatus(3);
            returnOrder.setReallyAmt(new BigDecimal(amt));
            returnOrder.setOutRefundNo(tkNo);
            returnOrderMapper.updateReturnOrder(returnOrder);
            ReturnPayLog returnPayLog = new ReturnPayLog();
            returnPayLog.setOrderNo(returnOrder.getOrderNo());
            returnPayLog.setAmount(new BigDecimal(amt));
            returnPayLog.setReturnOrderId(returnOrder.getId());
            returnPayLog.setReturnPayType(payThoroughfare.getPayProvider());
            returnPayLog.setReturnPayThoroughfareId(payThoroughfare.getId());
            returnPayLogMapper.insert(returnPayLog);
        } else {
            System.out.println("调用失败，原因：" + response );
            throw new ServiceException("退款失败，原因:" + response.getSubMsg());
        }
    }

    /**
     * 银联退款
     * @param payLog                订单支付记录
     * @param amt                   金额
     */
    private void unionPayRefund(PayLog payLog, Double amt, PayThoroughfare payThoroughfare,Long id) throws Exception {
        UnionPayParams unionPayParams = JSONUtil.toBean(payThoroughfare.getConfig(), UnionPayParams.class);
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));    // 报文请求时间
        // 商户订单号
        json.put("merOrderId",payLog.getOrderNoPay());
        // 业务类型
        json.put("instMid", "APPDEFAULT");
        // 商户号
        json.put("mid", unionPayParams.getMid());
        // 终端号
        json.put("tid", unionPayParams.getTid());
        // 退款金额
        json.put("refundAmount",(int)(amt * 100));
        // 退款订单号
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String tkNo = unionPayParams.getOrderPrefix() + snowflake.nextId() + "";
        json.put("refundOrderId",tkNo);      // 退款单号
        //OPEN-BODY-SIG 方式
        String authorization = getOpenBodySig(unionPayParams.getAppId(), unionPayParams.getAppKey(), json.toString());
        String send = send(unionPayParams.getRefundUrl(), json.toString() , authorization);
        log.info("响应信息：【{}】",send);
        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(send);
        if ("SUCCESS".equals(jsonObject.getStr("errCode")) ){
            ReturnOrder returnOrder = returnOrderMapper.selectReturnOrderById(id);
            returnOrder.setStatus(3);
            returnOrder.setReallyAmt(new BigDecimal(amt));
            returnOrder.setOutRefundNo(tkNo);
            returnOrderMapper.updateReturnOrder(returnOrder);
            ReturnPayLog returnPayLog = new ReturnPayLog();
            returnPayLog.setOrderNo(returnOrder.getOrderNo());
            returnPayLog.setAmount(new BigDecimal(amt));
            returnPayLog.setReturnOrderId(returnOrder.getId());
            returnPayLog.setReturnPayType(payThoroughfare.getPayProvider());
            returnPayLog.setReturnPayThoroughfareId(payThoroughfare.getId());
            returnPayLogMapper.insert(returnPayLog);
        }else {
            throw new ServiceException("退款失败,原因" + jsonObject.getStr("errMsg"));
        }
    }

    /**
     * 微信退款
     * @param order
     * @param amt
     * @param weChatAppParams
     */
    private void weChatAppRefund(Order order, Double amt, PayThoroughfare payThoroughfare,Long id) throws IOException {
        WeChatAppParams weChatAppParams = JSONUtil.toBean(payThoroughfare.getConfig(), WeChatAppParams.class);
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/refund/domestic/refunds");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String tkNo=sdf.format(new Date()) + (int) ((Math.random() * 9 + 1) * 1000)+"tk";

        PayLog payLog = payLogMapper.selectById(order.getPayLogId());

        JSONObject req=new JSONObject();
        req.put("out_trade_no", payLog.getOrderNoPay());//
        req.put("out_refund_no", tkNo);//退款单号
        JSONObject amount = new JSONObject();
        int total = 0;
        if(order.getAmount()!=null){
            total = order.getAmount().multiply(new BigDecimal("100")).intValue();
        }
        else {
            total=orderMapper.getTotal(order.getPayNo());
        }
        int refundAmt=(int)(amt * 100);
        amount.put("total",total);
        amount.put("refund",refundAmt);
        amount.put("currency","CNY");
        req.put("amount", amount);

        httpPost.setEntity(new StringEntity(req.toString(), "UTF-8"));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        JSONObject json=JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        System.out.println(json);
        if(json.getString("code")!=null){
            throw new ServiceException(json.getString("message"));
        }
        ReturnOrder rUpdate=new ReturnOrder();
        rUpdate.setId(id);
        rUpdate.setStatus(3);
        rUpdate.setReallyAmt(new BigDecimal(amt));
        rUpdate.setOutRefundNo(tkNo);
        returnOrderMapper.updateReturnOrder(rUpdate);
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
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity1 = response.getEntity();
        String resStr = null;
        if (entity1 != null) {
            resStr = EntityUtils.toString(entity1, "UTF-8");
        }
        httpClient.close();
        response.close();
        return resStr;
    }
}
