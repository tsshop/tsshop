package com.shop.tsshop.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;

import com.alibaba.fastjson.JSONArray;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.enums.PayEnums;
import com.shop.tsshop.web.enums.StorageTypeEnum;
import com.shop.tsshop.web.mapper.GoodsTypeMapper;
import com.shop.tsshop.web.mapper.PayThoroughfareMapper;
import com.shop.tsshop.web.model.domain.GoodsType;
import com.shop.tsshop.web.model.domain.PayThoroughfare;
import com.shop.tsshop.web.model.domain.PayType;
import com.shop.tsshop.web.model.domain.TsshopConfigStorage;
import com.shop.tsshop.web.model.domain.pay.AlipayParams;
import com.shop.tsshop.web.model.domain.pay.PayTypeVo;
import com.shop.tsshop.web.model.domain.pay.UnionPayParams;
import com.shop.tsshop.web.model.domain.pay.WeChatAppParams;
import com.shop.tsshop.web.service.PayThoroughfareService;
import com.shop.tsshop.web.service.PayTypeService;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.util.LocalStorageUtils;
import com.shop.tsshop.web.util.OssUtil;
import com.shop.tsshop.web.util.PayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author : tsshop
 * @date : 2023-4-23
 */
@Slf4j
@Service
public class PayThoroughfareServiceImpl extends ServiceImpl<PayThoroughfareMapper, PayThoroughfare> implements PayThoroughfareService {


    @Autowired
    private PayTypeService payTypeService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private OssUtil ossUtil;

    @Autowired
    private LocalStorageUtils localStorageUtils;

    @Override
    @PostConstruct
    public void initializationParams(){

        log.error("更新支付参数开始");

        List<PayThoroughfare> payThoroughfareList = list(new LambdaQueryWrapper<PayThoroughfare>()
                .eq(PayThoroughfare::getIsUse,true));
        log.info("支付通道数量：" + payThoroughfareList.size());
        if (CollectionUtil.isEmpty(payThoroughfareList)){
            PayUtil.payTypeVos = new ArrayList<>();
            PayUtil.payThoroughfareMap = new ConcurrentHashMap<>();
            return;
        }
        payThoroughfareList.forEach(item -> {
            if (PayEnums.ProviderEnum.WE_CHAT_PAY.getCode().equals(item.getPayProvider())){
                WeChatAppParams weChatAppParams = JSONUtil.toBean(item.getConfig(), WeChatAppParams.class);
                item.setWeChatAppParams(weChatAppParams);
            }
            if (PayEnums.ProviderEnum.ALI_PAY.getCode().equals(item.getPayProvider())){
                AlipayParams alipayParams = JSONUtil.toBean(item.getConfig(), AlipayParams.class);
                try{
                    AlipayClient alipayClient = null;
                    String config = (String) redisTemplate.opsForValue().get("config:storage");
                    if (ObjectUtil.isNull(config)){
                        throw new RuntimeException("存储配置信息有误请，前往后台配置");
                    }
                    TsshopConfigStorage tsshopConfigStorage = JSONUtil.toBean(config, TsshopConfigStorage.class);
                    if (StorageTypeEnum.ALI_OSS.getCode().equals(tsshopConfigStorage.getStorageType())){
                        alipayClient = PayUtil.getAliPay(alipayParams.getAppId(),alipayParams.getMerchantPrivateKey(),
                                ossUtil.getCertFile(tsshopConfigStorage,alipayParams.getMerchantCertPath()).getAbsolutePath(),
                                ossUtil.getCertFile(tsshopConfigStorage,alipayParams.getAlipayCertPath()).getAbsolutePath(),
                                ossUtil.getCertFile(tsshopConfigStorage,alipayParams.getAlipayRootCertPath()).getAbsolutePath());
                    }else if (StorageTypeEnum.LOCAL_STORAGE.getCode().equals(tsshopConfigStorage.getStorageType())){
                        alipayClient = PayUtil.getAliPay(alipayParams.getAppId(),alipayParams.getMerchantPrivateKey(),
                                localStorageUtils.getCertFile(alipayParams.getMerchantCertPath()).getAbsolutePath(),
                                localStorageUtils.getCertFile(alipayParams.getAlipayCertPath()).getAbsolutePath(),
                                localStorageUtils.getCertFile(alipayParams.getAlipayRootCertPath()).getAbsolutePath());
                    }
                    alipayParams.setAlipayClient(alipayClient);
                }catch (AlipayApiException e){
                    log.error("支付通道"+item.getName()+"alipayClient初始化失败");
                    log.error(e.getErrMsg());
                    e.printStackTrace();
                }
                item.setAlipayParams(alipayParams);
            }else if (PayEnums.ProviderEnum.UNION_PAY.getCode().equals(item.getPayProvider())){
                UnionPayParams unionPayParams = JSONUtil.toBean(item.getConfig(), UnionPayParams.class);
                item.setUnionPayParams(unionPayParams);
            }
        });
        Map<Long,PayThoroughfare> payThoroughfareMap = payThoroughfareList.stream().collect(Collectors.toMap(PayThoroughfare::getId, item -> item));
        ConcurrentHashMap<Long,PayThoroughfare>  payThoroughfareConcurrentHashMap = new ConcurrentHashMap<>();
        payThoroughfareConcurrentHashMap.putAll(payThoroughfareMap);
        PayUtil.payThoroughfareMap = payThoroughfareConcurrentHashMap;

        List<PayType> payTypeList = Optional.ofNullable(payTypeService.list()).orElseGet(() -> new ArrayList<>());
        Map<String,PayType> payTypeMap = payTypeList.stream().collect(Collectors.toMap(PayType::getType , item -> item));

        List<PayTypeVo> payTypeVos = new ArrayList<>();

        PayUtil.payThoroughfareMap.forEach((key,value) -> {
            List<String> list = (List<String>) JSONArray.parse(value.getPayTypes());
            list.forEach(item -> {
                PayType payType = payTypeMap.get(item);
                if (ObjectUtil.isNotEmpty(payType)){
                    PayTypeVo payTypeVo = new PayTypeVo()
                            .setPayThoroughfareId(value.getId())
                            .setType(payType.getType())
                            .setLogo(payType.getLogo())
                            .setName(payType.getName());
                    payTypeVos.add(payTypeVo);
                }
            });
        });

        PayUtil.payTypeVos = payTypeVos;

        System.out.println("payThoroughfareMap:"+PayUtil.payThoroughfareMap);
        System.out.println("payTypeVos:"+PayUtil.payTypeVos);


        log.error("更新支付参数结束");
    }


}
