package com.ts.shop.controller;

import com.ts.common.core.domain.AjaxResult;
import com.ts.shop.domain.dto.DayuMessageConfigDto;
import com.ts.shop.domain.dto.MessageConfigDto;
import com.ts.shop.service.TsshopConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName MessageConfigController
 * @Author TS SHOP
 * @create 2023/6/6
 */
@RestController
@RequestMapping("/messageConfig")
public class MessageConfigController {

    @Resource
    TsshopConfigService tsshopConfigService;


    @PreAuthorize("@ss.hasPermi('appConfig:message:info')")
    @GetMapping("/info")
    public AjaxResult getMessageConfigInfo() {
        return tsshopConfigService.info();
    }
    @PreAuthorize("@ss.hasPermi('appConfig:message:edit')")
    @PostMapping("/edit")
    public AjaxResult editMessageConfig(@RequestBody MessageConfigDto dto) {
        return tsshopConfigService.edit(dto);
    }
    @PreAuthorize("@ss.hasPermi('appConfig:message:dayuinfo')")
    @GetMapping("/dayuInfo")
    public AjaxResult getDayuMessageConfigInfo() {
        return tsshopConfigService.dayuInfo();
    }
    @PreAuthorize("@ss.hasPermi('appConfig:message:dayuedit')")
    @PostMapping("/dayuEdit")
    public AjaxResult editDayuMessageConfig(@RequestBody DayuMessageConfigDto dayuMessageConfigDto) {
        return tsshopConfigService.editDayu(dayuMessageConfigDto);
    }

}
