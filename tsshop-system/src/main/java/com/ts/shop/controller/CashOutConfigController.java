package com.ts.shop.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.core.redis.RedisCache;
import com.ts.shop.domain.LiveStoreWithdrawalsConfig;
import com.ts.shop.domain.TsshopConfig;
import com.ts.shop.domain.param.CashOutConfigParams;
import com.ts.shop.enmus.CashOutConfigEnmu;
import com.ts.shop.mapper.TsshopConfigMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @ClassName CashOutConfigController
 * @Author TS SHOP
 * @create 2023/6/6
 */
@RestController
@RequestMapping("/cash/out")
public class CashOutConfigController {
    @Resource
    TsshopConfigMapper tsshopConfigMapper;

    @Resource
    RedisCache redisCache;
    @PreAuthorize("@ss.hasPermi('appConfig:cashout:info')")
    @GetMapping("/info")
    public AjaxResult getCashOutConfig () {
        LiveStoreWithdrawalsConfig liveStoreWithdrawalsConfig = new LiveStoreWithdrawalsConfig();
        TsshopConfig minWithdrawAmt = getConfigByCode(CashOutConfigEnmu.MIN_WITHDRAW_AMT.getCode());
        TsshopConfig maxWithdrawAmt = getConfigByCode(CashOutConfigEnmu.MAX_WITHDRAW_AMT.getCode());
        TsshopConfig allowableWithdrawal = getConfigByCode(CashOutConfigEnmu.ALLOWABLE_WITHDRAWAL.getCode());
        TsshopConfig allowableDecimal = getConfigByCode(CashOutConfigEnmu.ALLOWABLE_DECIMAL.getCode());
        TsshopConfig withdrawPeriod = getConfigByCode(CashOutConfigEnmu.WITHDRAW_PERIOD.getCode());
        TsshopConfig withdrawNum = getConfigByCode(CashOutConfigEnmu.WITHDRAW_NUM.getCode());
        TsshopConfig withdrawRate = getConfigByCode(CashOutConfigEnmu.WITHDRAW_RATE.getCode());

        if (minWithdrawAmt != null) {
            liveStoreWithdrawalsConfig.setMinWithdrawAmt(new BigDecimal(minWithdrawAmt.getConfigValue()));
        }
        if (maxWithdrawAmt != null) {
            liveStoreWithdrawalsConfig.setMaxWithdrawAmt(new BigDecimal(maxWithdrawAmt.getConfigValue()));
        }
        if (allowableWithdrawal != null) {
            liveStoreWithdrawalsConfig.setAllowableWithdrawal(Boolean.valueOf(allowableWithdrawal.getConfigValue()));
        }
        if (allowableDecimal != null) {
            liveStoreWithdrawalsConfig.setAllowableDecimal(Boolean.valueOf(allowableDecimal.getConfigValue()));
        }
        if (withdrawPeriod != null) {
            liveStoreWithdrawalsConfig.setWithdrawPeriod(Integer.valueOf(withdrawPeriod.getConfigValue()));
        }
        if (withdrawNum != null) {
            liveStoreWithdrawalsConfig.setWithdrawNum(Long.valueOf(withdrawNum.getConfigValue()));
        }
        if (withdrawRate != null) {
            liveStoreWithdrawalsConfig.setWithdrawRate(withdrawRate.getConfigValue());
        }
        return AjaxResult.success(liveStoreWithdrawalsConfig);
    }
    @PreAuthorize("@ss.hasPermi('appConfig:cashout:edit')")
    @PostMapping("/edit")
    public AjaxResult editCashOutConfig (@RequestBody LiveStoreWithdrawalsConfig dto) {
        updateTsshopConfig(CashOutConfigEnmu.MIN_WITHDRAW_AMT.getCode(), String.valueOf(dto.getMinWithdrawAmt()));
        updateTsshopConfig(CashOutConfigEnmu.MAX_WITHDRAW_AMT.getCode(), String.valueOf(dto.getMaxWithdrawAmt()));
        updateTsshopConfig(CashOutConfigEnmu.ALLOWABLE_WITHDRAWAL.getCode(), String.valueOf(dto.getAllowableWithdrawal()));
        updateTsshopConfig(CashOutConfigEnmu.ALLOWABLE_DECIMAL.getCode(), String.valueOf(dto.getAllowableDecimal()));
        updateTsshopConfig(CashOutConfigEnmu.WITHDRAW_PERIOD.getCode(), String.valueOf(dto.getWithdrawPeriod()));
        updateTsshopConfig(CashOutConfigEnmu.WITHDRAW_NUM.getCode(), String.valueOf(dto.getWithdrawNum()));
        updateTsshopConfig(CashOutConfigEnmu.WITHDRAW_RATE.getCode(), String.valueOf(dto.getWithdrawRate()));
        CashOutConfigParams cashOutConfigParams = new CashOutConfigParams();
        BeanUtil.copyProperties(dto,cashOutConfigParams);
        redisCache.setCacheObject("config:cash_out", JSON.toJSONString(cashOutConfigParams));
        return AjaxResult.success();
    }

    private void updateTsshopConfig(String configKey, String configValue) {
        TsshopConfig config = tsshopConfigMapper.selectOne(new LambdaQueryWrapper<TsshopConfig>().eq(TsshopConfig::getConfigKey, configKey));
        if (config == null) {
            config = new TsshopConfig();
            config.setConfigKey(configKey);
            config.setConfigValue(configValue);
            tsshopConfigMapper.insert(config);
        } else {
            config.setConfigValue(configValue);
            tsshopConfigMapper.updateById(config);
        }
    }

    private TsshopConfig getConfigByCode(String configCode) {
        return tsshopConfigMapper.selectOne(new LambdaQueryWrapper<TsshopConfig>().eq(TsshopConfig::getConfigKey, configCode));
    }

}
