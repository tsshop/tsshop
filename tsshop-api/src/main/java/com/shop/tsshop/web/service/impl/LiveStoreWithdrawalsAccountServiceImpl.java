package com.shop.tsshop.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.enums.PayEnums;
import com.shop.tsshop.web.mapper.LiveStoreMapper;
import com.shop.tsshop.web.mapper.LiveStoreWithdrawalsConfigMapper;
import com.shop.tsshop.web.model.domain.*;
import com.shop.tsshop.web.model.domain.pay.PayTypeVo;
import com.shop.tsshop.web.model.dto.CreateWithdrawalsAccountDto;
import com.shop.tsshop.web.model.dto.DelWithdrawalsAccountDto;
import com.shop.tsshop.web.model.vo.AmountTypeVo;
import com.shop.tsshop.web.service.PayThoroughfareService;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.util.PayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.mapper.LiveStoreWithdrawalsAccountMapper;
import com.shop.tsshop.web.service.LiveStoreWithdrawalsAccountService;
/**
 * @ClassName LiveStoreWithdrawalsAccountServiceImpl
 * @Author TS SHOP
 * @create 2023/6/1
 */

@Service
@Slf4j
public class LiveStoreWithdrawalsAccountServiceImpl extends ServiceImpl<LiveStoreWithdrawalsAccountMapper, LiveStoreWithdrawalsAccount> implements LiveStoreWithdrawalsAccountService{
    @Resource
    RedisService redisService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    LiveStoreMapper liveStoreMapper;

    @Resource
    PayThoroughfareService payThoroughfareService;

    @Resource
    LiveStoreWithdrawalsConfigMapper liveStoreWithdrawalsConfigMapper;

    @Resource
    LiveStoreWithdrawalsAccountMapper liveStoreWithdrawalsAccountMapper;

    @Override
    public ApiResult<Object> createWithdrawalsAccount(CreateWithdrawalsAccountDto dto, HttpServletRequest request) {
        LiveStore liveStore = getLiveStore(request);
        if (ObjectUtil.isNull(liveStore)){
            return ApiResult.fail("店铺信息异常，请刷新重试");
        }
        Object userCode = redisTemplate.opsForValue().get("tsshop:cashout:msgCode:" + liveStore.getStorePhone());
        log.info("redis Code [{}]",userCode);
        if (ObjectUtil.isEmpty(userCode) || !dto.getCode().equals(userCode)) {
            return ApiResult.fail("验证码错误或已过期");
        }
        LiveStoreWithdrawalsAccount liveStoreWithdrawalsAccount = new LiveStoreWithdrawalsAccount();
        liveStoreWithdrawalsAccount.setLiveStoreId(liveStore.getId());
        liveStoreWithdrawalsAccount.setAccountNumber(dto.getAccountNumber());
        liveStoreWithdrawalsAccount.setFullName(dto.getFullName());
        liveStoreWithdrawalsAccount.setAccountType(dto.getAccountType());
        liveStoreWithdrawalsAccountMapper.insert(liveStoreWithdrawalsAccount);
        return ApiResult.ok();
    }

    @Override
    public ApiResult<Object> getWithdrawalsAccount(String accountType,HttpServletRequest request) {
        LiveStore liveStore = getLiveStore(request);
        if (ObjectUtil.isNull(liveStore)){
            return ApiResult.fail("店铺信息异常，请刷新重试");
        }
        List<LiveStoreWithdrawalsAccount> liveStoreWithdrawalsAccounts = liveStoreWithdrawalsAccountMapper
                .selectList(new LambdaQueryWrapper<LiveStoreWithdrawalsAccount>()
                        .eq(LiveStoreWithdrawalsAccount::getLiveStoreId, liveStore.getId())
                        .eq(accountType != null,LiveStoreWithdrawalsAccount::getAccountType,accountType)
                        .eq(LiveStoreWithdrawalsAccount::getDeleted, false));
        if (CollUtil.isNotEmpty(liveStoreWithdrawalsAccounts)){
            liveStoreWithdrawalsAccounts.forEach(item ->{
                if (item.getAccountType().equals(PayEnums.PayTypeEnum.ALI_PAY_APP.getCode())){
                    item.setAccountType("支付宝");
                }
            });
        }
        return ApiResult.ok(liveStoreWithdrawalsAccounts);
    }

    @Override
    public ApiResult<Object> delWithdrawalsAccount(DelWithdrawalsAccountDto dto, HttpServletRequest request) {
        LiveStore liveStore = getLiveStore(request);
        if (ObjectUtil.isNull(liveStore)){
            return ApiResult.fail("店铺信息异常，请刷新重试");
        }
        List<LiveStoreWithdrawalsAccount> liveStoreWithdrawalsAccounts = liveStoreWithdrawalsAccountMapper.selectBatchIds(dto.getIds());
        for (LiveStoreWithdrawalsAccount liveStoreWithdrawalsAccount : liveStoreWithdrawalsAccounts) {
            if (ObjectUtil.isNull(liveStoreWithdrawalsAccount)){
                return ApiResult.fail("账户不存在");
            }
            if (!Objects.equals(liveStoreWithdrawalsAccount.getLiveStoreId(), liveStore.getId())){
                return ApiResult.fail("您无权删除此账户");
            }
        }
        for (LiveStoreWithdrawalsAccount liveStoreWithdrawalsAccount : liveStoreWithdrawalsAccounts) {
            liveStoreWithdrawalsAccount.setDeleted(true);
            liveStoreWithdrawalsAccountMapper.updateById(liveStoreWithdrawalsAccount);
        }
        return ApiResult.ok();
    }

    @Override
    public ApiResult<Object> getAccountType() {
        LiveStoreWithdrawalsConfig liveStoreWithdrawalsConfig = liveStoreWithdrawalsConfigMapper.selectOne(new LambdaQueryWrapper<>());
        if (ObjectUtil.isNull(liveStoreWithdrawalsConfig)){
            return ApiResult.fail("系统未配置，请刷新重试");
        }
        Object obj = redisService.getObj("pay:updateTime");
        if (ObjectUtil.isNotEmpty(obj)){
            Long updateTime = (Long) obj;
            if (!PayUtil.updateTime.equals(updateTime)){
                payThoroughfareService.initializationParams();
                PayUtil.updateTime = updateTime;
            }
        }
        List<PayTypeVo> payTypeVos = new ArrayList<>();
        Map<Long, PayTypeVo> collect = PayUtil.payTypeVos.stream().filter(distinctByKey(PayTypeVo::getPayThoroughfareId)).collect(Collectors.toMap(PayTypeVo::getPayThoroughfareId, Function.identity()));
        for (Long key : PayUtil.payThoroughfareMap.keySet()) {
            PayThoroughfare value = PayUtil.payThoroughfareMap.get(key);
            if (value.getSupportWithdrawals()){
                payTypeVos.add(collect.get(value.getId()));
            }
        }
        return ApiResult.ok(payTypeVos);
    }
    // 定义去重方法 distinctByKey
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
    public LiveStore getLiveStore(HttpServletRequest request){
        User currentUser = redisService.getCurrentUser(request);
        return liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getUserId, currentUser.getId()));
    }

}
