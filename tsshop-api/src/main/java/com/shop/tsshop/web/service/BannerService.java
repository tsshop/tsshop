package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.Banner;

import java.util.List;

/**
 * <p>
 * 用户首页轮播图片表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
public interface BannerService extends IService<Banner> {

    /**
     * 获取 Banner
     * @return              统一返回
     */
    List<Banner> getBanner();
}
