package com.shop.tsshop.web.service.impl;

import cn.hutool.json.JSONUtil;
import com.shop.tsshop.common.utils.StringUtils;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.enums.AgreementConfigEnmu;
import com.shop.tsshop.web.model.vo.AgreementConfigVo;
import com.shop.tsshop.web.service.RedisService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.model.domain.TsshopConfig;
import com.shop.tsshop.web.mapper.TsshopConfigMapper;
import com.shop.tsshop.web.service.TsshopConfigService;
/**
 * @ClassName TsshopConfigServiceImpl
 * @Author TS SHOP
 * @create 2023/6/6
 */

@Service
public class TsshopConfigServiceImpl extends ServiceImpl<TsshopConfigMapper, TsshopConfig> implements TsshopConfigService{

    @Resource
    RedisService redisService;

    @Override
    public ApiResult<Object> getAgreementInfo() {
        AgreementConfigVo agreementConfigVo = new AgreementConfigVo();
        String service = redisService.getString("config:" + AgreementConfigEnmu.SERVICE_AGREEMENT.getCode().toLowerCase());
        if (StringUtils.isNotEmpty(service)){
            TsshopConfig serviceConfig = JSONUtil.toBean(service, TsshopConfig.class);
            agreementConfigVo.setServiceTitle(serviceConfig.getTitle());
            agreementConfigVo.setServiceAgreement(serviceConfig.getConfigValue());
        }

        String privacy = redisService.getString("config:" + AgreementConfigEnmu.PRIVACY_AGREEMENT.getCode().toLowerCase());
        if (StringUtils.isNotEmpty(privacy)){
            TsshopConfig privacyConfig = JSONUtil.toBean(privacy, TsshopConfig.class);
            agreementConfigVo.setPrivacyTitle(privacyConfig.getTitle());
            agreementConfigVo.setPrivacyAgreement(privacyConfig.getConfigValue());
        }
        String pay = redisService.getString("config:" + AgreementConfigEnmu.PAY_AGREEMENT.getCode().toLowerCase());
        if (StringUtils.isNotEmpty(pay)){
            TsshopConfig payConfig = JSONUtil.toBean(pay, TsshopConfig.class);
            agreementConfigVo.setPayTitle(payConfig.getTitle());
            agreementConfigVo.setPayAgreement(payConfig.getConfigValue());
        }
        return ApiResult.ok(agreementConfigVo);
    }
}
