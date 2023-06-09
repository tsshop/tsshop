package com.ts.shop.controller;

import com.ts.common.annotation.Anonymous;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.core.redis.RedisCache;
import com.ts.shop.domain.dto.AgreementConfigDto;
import com.ts.shop.domain.dto.AppConfigDto;
import com.ts.shop.domain.dto.WxLiteConfigDto;
import com.ts.shop.service.TsshopConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName AppConfig
 * @Author TS SHOP
 * @create 2023/6/7
 */
@RestController
@RequestMapping("/appConfig")
public class AppConfigController {

    @Resource
    TsshopConfigService tsshopConfigService;

    @GetMapping("/info")
    @Anonymous
    public AjaxResult getAppConfigInfo() {
        return tsshopConfigService.getAppConfigInfo();
    }
    @PreAuthorize("@ss.hasPermi('appConfig:app:edit')")
    @PostMapping("/edit")
    public AjaxResult editAppConfig(@RequestBody AppConfigDto appConfigDto) {
        return tsshopConfigService.editAppConfig(appConfigDto);
    }

    @PreAuthorize("@ss.hasPermi('appConfig:lite:info')")
    @GetMapping("/liteInfo")
    public AjaxResult getLiteConfigInfo() {
        return tsshopConfigService.getLiteConfigInfo();
    }
    @PreAuthorize("@ss.hasPermi('appConfig:lite:eidt')")
    @PostMapping("/editLite")
    public AjaxResult editWxLiteConfig(@RequestBody WxLiteConfigDto wxLiteConfigDto) {
        return tsshopConfigService.editWxLiteConfig(wxLiteConfigDto);
    }
    @PreAuthorize("@ss.hasPermi('appConfig:agreement:info')")
    @GetMapping("/agreementInfo")
    public AjaxResult getAgreementConfigInfo() {
        return tsshopConfigService.getAgreementConfigInfo();
    }
    @PreAuthorize("@ss.hasPermi('appConfig:agreement:edit')")
    @PostMapping("/editAgreementConfig")
    public AjaxResult editAgreementConfig(@RequestBody AgreementConfigDto agreementConfigDto) {
        return tsshopConfigService.editAgreementConfig(agreementConfigDto);
    }

}
