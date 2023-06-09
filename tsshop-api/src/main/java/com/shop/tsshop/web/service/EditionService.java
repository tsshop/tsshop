package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.Edition;

/**
 * <p>
 * 版本表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
public interface EditionService extends IService<Edition> {

    /**
     * 获取版本列表
     * @param type            类型
     * @return                统一返回
     */
    Object getEdition(Integer type);
}
