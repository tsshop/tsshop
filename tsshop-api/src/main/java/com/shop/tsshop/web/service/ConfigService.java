package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.Config;

/**
 * <p>
 * 配置 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
public interface ConfigService extends IService<Config> {

    /**
     * 获取配置信息
     * @return
     */
    Object getConfigList();
}
