package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.TsshopConfig;
    /**
 * @ClassName TsshopConfigService
 * @Author TsShop
 * @create 2023/6/6
 */

public interface TsshopConfigService extends IService<TsshopConfig>{


    /**
     * 获取协议配置信息系
     * @return           统一返回
     */
    ApiResult<Object> getAgreementInfo();
}
