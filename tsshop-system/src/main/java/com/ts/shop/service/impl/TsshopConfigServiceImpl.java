package com.ts.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.core.redis.RedisCache;
import com.ts.shop.domain.MessageConfig;
import com.ts.shop.domain.dto.*;
import com.ts.shop.domain.param.DayuConfigParams;
import com.ts.shop.domain.param.YunjiConfigPatams;
import com.ts.shop.domain.vo.*;
import com.ts.shop.enmus.AgreementConfigEnmu;
import com.ts.shop.enmus.AppConfigEnmu;
import com.ts.shop.enmus.MessageConfigEnmu;
import com.ts.shop.enmus.WxLiteConfigEnmu;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.shop.mapper.TsshopConfigMapper;
import com.ts.shop.domain.TsshopConfig;
import com.ts.shop.service.TsshopConfigService;
/**
 * @ClassName TsshopConfigServiceImpl
 * @Author TS SHOP
 * @create 2023/6/6
 */

@Service
public class TsshopConfigServiceImpl extends ServiceImpl<TsshopConfigMapper, TsshopConfig> implements TsshopConfigService{

    @Resource
    TsshopConfigMapper tsshopConfigMapper;

    @Resource
    RedisCache redisCache;

    @Override
    public AjaxResult info() {
        MessageConfigVo messageConfigVo = new MessageConfigVo();
        TsshopConfig openStatus = getConfigByCode(MessageConfigEnmu.YUNJI_OPEN_STATUS.getCode());
        TsshopConfig accessKey = getConfigByCode(MessageConfigEnmu.YUNJI_ACCESSKEY.getCode());
        TsshopConfig accessSecret = getConfigByCode(MessageConfigEnmu.YUNJI_ACCESSSECRET.getCode());
        TsshopConfig signCode = getConfigByCode(MessageConfigEnmu.YUNJI_SIGNCODE.getCode());
        TsshopConfig templateCode = getConfigByCode(MessageConfigEnmu.YUNJI_TEMPLATECODE.getCode());
        TsshopConfig classificationSecret = getConfigByCode(MessageConfigEnmu.YUNJI_CLASSIFICATIONSECRET.getCode());
        if (openStatus != null) {
            messageConfigVo.setOpenStatus(Boolean.valueOf(openStatus.getConfigValue()));
        }
        if (accessKey != null) {
            messageConfigVo.setAccessKey(accessKey.getConfigValue());
        }
        if (accessSecret != null) {
            messageConfigVo.setAccessSecret(accessSecret.getConfigValue());
        }
        if (signCode != null) {
            messageConfigVo.setSignCode(signCode.getConfigValue());
        }
        if (templateCode != null) {
            messageConfigVo.setTemplateCode(templateCode.getConfigValue());
        }
        if (classificationSecret != null) {
            messageConfigVo.setClassificationSecret(classificationSecret.getConfigValue());
        }
        return AjaxResult.success(messageConfigVo);
    }

    @Override
    public AjaxResult edit(MessageConfigDto dto) {
        if (dto.getOpenStatus() && Boolean.parseBoolean(getConfigByCode(MessageConfigEnmu.ALI_DAYU_OPEN_STATUS.getCode()).getConfigValue())) {
            return AjaxResult.error("仅允许开启一个短信配置");
        }
        updateTsshopConfig(MessageConfigEnmu.YUNJI_OPEN_STATUS.getCode(), String.valueOf(dto.getOpenStatus()));
        updateTsshopConfig(MessageConfigEnmu.YUNJI_ACCESSKEY.getCode(), dto.getAccessKey());
        updateTsshopConfig(MessageConfigEnmu.YUNJI_ACCESSSECRET.getCode(), dto.getAccessSecret());
        updateTsshopConfig(MessageConfigEnmu.YUNJI_SIGNCODE.getCode(), dto.getSignCode());
        updateTsshopConfig(MessageConfigEnmu.YUNJI_TEMPLATECODE.getCode(), dto.getTemplateCode());
        updateTsshopConfig(MessageConfigEnmu.YUNJI_CLASSIFICATIONSECRET.getCode(), dto.getClassificationSecret());
        if (dto.getOpenStatus()){
            MessageConfig messageConfig = new MessageConfig();
            messageConfig.setType(MessageConfigEnmu.YUNJI_OPEN_STATUS.getCode());
            YunjiConfigPatams yunjiConfigPatams = new YunjiConfigPatams();
            BeanUtil.copyProperties(dto,yunjiConfigPatams);
            messageConfig.setConfig(JSON.toJSONString(yunjiConfigPatams));
            redisCache.setCacheObject("config:message",JSON.toJSONString(messageConfig));
        }else {
            redisCache.deleteObject("config:message");
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult dayuInfo() {
        DayuMessageConfigVo dayuMessageConfigVo = new DayuMessageConfigVo();
        TsshopConfig openStatus = getConfigByCode(MessageConfigEnmu.ALI_DAYU_OPEN_STATUS.getCode());
        TsshopConfig accessKeyId = getConfigByCode(MessageConfigEnmu.ALI_DAYU_ACCESSKEYID.getCode());
        TsshopConfig accessKeySecret = getConfigByCode(MessageConfigEnmu.ALI_DAYU_ACCESSKEYSECRET.getCode());
        TsshopConfig signName = getConfigByCode(MessageConfigEnmu.ALI_DAYU_SIGNNAME.getCode());
        TsshopConfig templateCode = getConfigByCode(MessageConfigEnmu.ALI_DAYU_TEMPLATECODE.getCode());
        if (openStatus != null) {
            dayuMessageConfigVo.setOpenStatus(Boolean.valueOf(openStatus.getConfigValue()));
        }
        if (accessKeyId != null) {
            dayuMessageConfigVo.setAccessKeyId(accessKeyId.getConfigValue());
        }
        if (accessKeySecret != null) {
            dayuMessageConfigVo.setAccessKeySecret(accessKeySecret.getConfigValue());
        }
        if (signName != null) {
            dayuMessageConfigVo.setSignName(signName.getConfigValue());
        }
        if (templateCode != null) {
            dayuMessageConfigVo.setTemplateCode(templateCode.getConfigValue());
        }
        return AjaxResult.success(dayuMessageConfigVo);
    }

    @Override
    public AjaxResult editDayu(DayuMessageConfigDto dto) {
        if (dto.getOpenStatus() && Boolean.parseBoolean(getConfigByCode(MessageConfigEnmu.YUNJI_OPEN_STATUS.getCode()).getConfigValue())) {
            return AjaxResult.error("仅允许开启一个短信配置");
        }
        updateTsshopConfig(MessageConfigEnmu.ALI_DAYU_OPEN_STATUS.getCode(), String.valueOf(dto.getOpenStatus()));
        updateTsshopConfig(MessageConfigEnmu.ALI_DAYU_ACCESSKEYID.getCode(), dto.getAccessKeyId());
        updateTsshopConfig(MessageConfigEnmu.ALI_DAYU_ACCESSKEYSECRET.getCode(), dto.getAccessKeySecret());
        updateTsshopConfig(MessageConfigEnmu.ALI_DAYU_SIGNNAME.getCode(), dto.getSignName());
        updateTsshopConfig(MessageConfigEnmu.ALI_DAYU_TEMPLATECODE.getCode(), dto.getTemplateCode());
        if (dto.getOpenStatus()){
            MessageConfig messageConfig = new MessageConfig();
            messageConfig.setType(MessageConfigEnmu.ALI_DAYU_OPEN_STATUS.getCode());
            DayuConfigParams dayuConfigParams = new DayuConfigParams();
            BeanUtil.copyProperties(dto,dayuConfigParams);
            messageConfig.setConfig(JSON.toJSONString(dayuConfigParams));
            redisCache.setCacheObject("config:message",JSON.toJSONString(messageConfig));
        }else {
            redisCache.deleteObject("config:message");
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult getAppConfigInfo() {
        AppConfigVo appConfigVo = new AppConfigVo();
        TsshopConfig appName = getConfigByCode(AppConfigEnmu.APP_NAME.getCode());
        TsshopConfig appIcon = getConfigByCode(AppConfigEnmu.APP_ICON.getCode());
        TsshopConfig defaultHeadPortrait = getConfigByCode(AppConfigEnmu.APP_DEFAULT_HEAD_PORTRAIT.getCode());
        if (appName != null) {
            appConfigVo.setAppName(appName.getConfigValue());
        }
        if (appIcon != null) {
            appConfigVo.setAppIcon(appIcon.getConfigValue());
        }
        if (defaultHeadPortrait != null) {
            appConfigVo.setDefaultHeadPortrait(defaultHeadPortrait.getConfigValue());
        }
        return AjaxResult.success(appConfigVo);
    }

    @Override
    public AjaxResult editAppConfig(AppConfigDto dto) {
        updateTsshopConfig(AppConfigEnmu.APP_NAME.getCode(), dto.getAppName());
        updateTsshopConfig(AppConfigEnmu.APP_ICON.getCode(), dto.getAppIcon());
        updateTsshopConfig(AppConfigEnmu.APP_DEFAULT_HEAD_PORTRAIT.getCode(), dto.getDefaultHeadPortrait());
        redisCache.setCacheObject("config:app_default_head_portrait",dto.getDefaultHeadPortrait());
        redisCache.setCacheObject("config:app_name",dto.getAppName());
        redisCache.setCacheObject("config:app_icon",dto.getAppIcon());
        return AjaxResult.success();
    }

    @Override
    public AjaxResult editWxLiteConfig(WxLiteConfigDto dto) {
        updateTsshopConfig(WxLiteConfigEnmu.WEI_XIN_LITE_APPID.getCode(), dto.getAppid());
        updateTsshopConfig(WxLiteConfigEnmu.WEI_XIN_LITE_APPSECRET.getCode(),dto.getAppSecret());
        redisCache.setCacheObject("config:wei_xin_lite_appid",dto.getAppid());
        redisCache.setCacheObject("config:wei_xin_lite_appsecret",dto.getAppSecret());
        return AjaxResult.success();
    }

    @Override
    public AjaxResult getLiteConfigInfo() {
        WxLiteConfigVo wxLiteConfigVo = new WxLiteConfigVo();
        TsshopConfig appid = getConfigByCode(WxLiteConfigEnmu.WEI_XIN_LITE_APPID.getCode());
        TsshopConfig appSecret = getConfigByCode(WxLiteConfigEnmu.WEI_XIN_LITE_APPSECRET.getCode());
        if (appid != null) {
            wxLiteConfigVo.setAppid(appid.getConfigValue());
        }
        if (appSecret != null) {
            wxLiteConfigVo.setAppSecret(appSecret.getConfigValue());
        }
        return AjaxResult.success(wxLiteConfigVo);
    }
    @Override
    public AjaxResult editAgreementConfig(AgreementConfigDto dto) {
        if (dto.getServiceAgreement() != null && dto.getServiceTitle() != null){
            updateTsshopConfig(AgreementConfigEnmu.SERVICE_AGREEMENT.getCode(),dto.getServiceAgreement(),dto.getServiceTitle());
        }
        if (dto.getPrivacyAgreement() != null && dto.getPrivacyTitle() != null){
            updateTsshopConfig(AgreementConfigEnmu.PRIVACY_AGREEMENT.getCode(),dto.getPrivacyAgreement(),dto.getPrivacyTitle());
        }
        if (dto.getPayAgreement() != null && dto.getPrivacyTitle() != null){
            updateTsshopConfig(AgreementConfigEnmu.PAY_AGREEMENT.getCode(),dto.getPayAgreement(),dto.getPayTitle());
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult getAgreementConfigInfo() {
        AgreementConfigVo agreementConfigVo = new AgreementConfigVo();
        TsshopConfig service = getConfigByCode(AgreementConfigEnmu.SERVICE_AGREEMENT.getCode());
        TsshopConfig privacy = getConfigByCode(AgreementConfigEnmu.PRIVACY_AGREEMENT.getCode());
        TsshopConfig pay = getConfigByCode(AgreementConfigEnmu.PAY_AGREEMENT.getCode());
        if (service != null) {
            agreementConfigVo.setServiceTitle(service.getTitle());
            agreementConfigVo.setServiceAgreement(service.getConfigValue());
        }
        if (privacy != null) {
            agreementConfigVo.setPrivacyTitle(privacy.getTitle());
            agreementConfigVo.setPrivacyAgreement(privacy.getConfigValue());
        }
        if (pay != null) {
            agreementConfigVo.setPayTitle(pay.getTitle());
            agreementConfigVo.setPayAgreement(pay.getConfigValue());
        }
        return AjaxResult.success(agreementConfigVo);
    }

    /**
     * 存储配置
     * @param configKey              key
     * @param configValue            value
     */
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
    /**
     * 存储配置
     * @param configKey              key
     * @param configValue            value
     */
    private void updateTsshopConfig(String configKey, String configValue,String title) {
        TsshopConfig config = tsshopConfigMapper.selectOne(new LambdaQueryWrapper<TsshopConfig>().eq(TsshopConfig::getConfigKey, configKey));
        if (config == null) {
            config = new TsshopConfig();
            config.setConfigKey(configKey);
            config.setConfigValue(configValue);
            config.setTitle(title);
            tsshopConfigMapper.insert(config);
            redisCache.setCacheObject("config:" + configKey.toLowerCase(),JSON.toJSONString(config));
        } else {
            config.setConfigValue(configValue);
            config.setTitle(title);
            tsshopConfigMapper.updateById(config);
            redisCache.setCacheObject("config:" + configKey.toLowerCase(),JSON.toJSONString(config));
        }
    }

    /**
     * 获取配置
     * @param configCode             key
     * @return                       config
     */
    private TsshopConfig getConfigByCode(String configCode) {
        return tsshopConfigMapper.selectOne(new LambdaQueryWrapper<TsshopConfig>().eq(TsshopConfig::getConfigKey, configCode));
    }


}
