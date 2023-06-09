package com.shop.tsshop.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.tsshop.web.model.domain.Banner;
import com.shop.tsshop.web.mapper.BannerMapper;
import com.shop.tsshop.web.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户首页轮播图片表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Override
    public List<Banner> getBanner() {
        return baseMapper.selectList(new LambdaQueryWrapper<Banner>().eq(Banner::getDeleted,0).orderByDesc(Banner::getSort));
    }
}
