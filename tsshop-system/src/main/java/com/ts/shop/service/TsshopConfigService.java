package com.ts.shop.service;

import com.ts.common.core.domain.AjaxResult;
import com.ts.shop.domain.TsshopConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.shop.domain.dto.*;

/**
 * @ClassName TsshopConfigService
 * @Author TS SHOP
 * @create 2023/6/6
 */

public interface TsshopConfigService extends IService<TsshopConfig>{


    /**
     * 获取云极短信配置
     * @return                 统一返回
     */
    AjaxResult info();

    /**
     * 编辑云极短信配置
     * @param dto              dto
     * @return                 统一返回
     */
    AjaxResult edit(MessageConfigDto dto);

    /**
     * 获取阿里大于短信配置
     * @return                 统一返回
     */
    AjaxResult dayuInfo();

    /**
     * 编辑阿里大于短信配置
     * @param dayuMessageConfigDto          dto
     * @return                              统一返回
     */
    AjaxResult editDayu(DayuMessageConfigDto dayuMessageConfigDto);

    /**
     * 获取 APP 配置内容
     * @return                              统一返回
     */
    AjaxResult getAppConfigInfo();

    /**
     * 修改 APP 配置
     * @param appConfigDto                  dto
     * @return                              统一返回
     */
    AjaxResult editAppConfig(AppConfigDto appConfigDto);

    /**
     * 编辑小程序配置
     * @param wxLiteConfigDto               dto
     * @return                              统一返回
     */
    AjaxResult editWxLiteConfig(WxLiteConfigDto wxLiteConfigDto);

    /**
     * 获取小程序配置信息
     * @return                              统一返回
     */
    AjaxResult getLiteConfigInfo();

    /**
     * 协议配置
     * @param agreementConfigDto            dto
     * @return                              统一返回
     */
    AjaxResult editAgreementConfig(AgreementConfigDto agreementConfigDto);

    /**
     * 获取协议配置信息
     * @return                              统一返回
     */
    AjaxResult getAgreementConfigInfo();
}
