package com.ts.shop.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.core.redis.RedisCache;
import com.ts.shop.domain.TsshopConfig;
import com.ts.shop.domain.dto.ExpressConfigDto;
import com.ts.shop.domain.param.ExpressParams;
import com.ts.shop.domain.vo.ExpressConfigVo;
import com.ts.shop.enmus.ExpressConfigEnmu;
import com.ts.shop.mapper.TsshopConfigMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName ExpressConfigController
 * @Author TS SHOP
 * @create 2023/6/6
 */
@RestController
@RequestMapping("/expressConfig")
public class ExpressConfigController {

    @Resource
    TsshopConfigMapper tsshopConfigMapper;

    @Resource
    RedisCache redisCache;
    @PreAuthorize("@ss.hasPermi('appConfig:kdniao:info')")
    @GetMapping("/info")
    public AjaxResult getExpressConfig() {
        ExpressConfigVo expressConfigVo = new ExpressConfigVo();
        TsshopConfig tsshopConfig = getConfigByCode(ExpressConfigEnmu.KDNIAO_APPID.getCode());
        TsshopConfig tsshopConfigAppKey = getConfigByCode(ExpressConfigEnmu.KDNIAO_APPKEY.getCode());
        TsshopConfig tsshopConfigEnable = getConfigByCode(ExpressConfigEnmu.KDNIAO_ENABLE.getCode());
        TsshopConfig tsshopConfigReqUrl = getConfigByCode(ExpressConfigEnmu.KDNIAO_REQURL.getCode());
        if (tsshopConfig != null) {
            expressConfigVo.setAppId(tsshopConfig.getConfigValue());
        }
        if (tsshopConfigAppKey != null) {
            expressConfigVo.setAppKey(tsshopConfigAppKey.getConfigValue());
        }
        if (tsshopConfigEnable != null) {
            expressConfigVo.setEnable(tsshopConfigEnable.getConfigValue());
        }
        if (tsshopConfigReqUrl != null) {
            expressConfigVo.setReqUrl(tsshopConfigReqUrl.getConfigValue());
        }
        return AjaxResult.success(expressConfigVo);
    }

    private TsshopConfig getConfigByCode(String configCode) {
        return tsshopConfigMapper.selectOne(new LambdaQueryWrapper<TsshopConfig>().eq(TsshopConfig::getConfigKey, configCode));
    }
    @PreAuthorize("@ss.hasPermi('appConfig:kdniao:edit')")
    @PostMapping("/edit")
    public AjaxResult editExpressConfig(@RequestBody ExpressConfigDto dto) {
        updateTsshopConfig(ExpressConfigEnmu.KDNIAO_APPID.getCode(), dto.getAppId());
        updateTsshopConfig(ExpressConfigEnmu.KDNIAO_APPKEY.getCode(), dto.getAppKey());
        updateTsshopConfig(ExpressConfigEnmu.KDNIAO_ENABLE.getCode(), String.valueOf(dto.getEnable()));
        updateTsshopConfig(ExpressConfigEnmu.KDNIAO_REQURL.getCode(), dto.getReqUrl());
        ExpressParams expressParams = new ExpressParams();
        BeanUtil.copyProperties(dto, expressParams);
        redisCache.setCacheObject("config:kdniao", JSON.toJSONString(expressParams));
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
}
