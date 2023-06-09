package com.shop.tsshop.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.PayThoroughfare;

/**
 * @author : tsshop
 * @date : 2023-4-23
 */
public interface PayThoroughfareService extends IService<PayThoroughfare> {

    /**
     * 初始化支付参数
     */
    void initializationParams();

}