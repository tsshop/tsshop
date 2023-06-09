package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.BannerMapper;
import com.ts.shop.domain.Banner;
import com.ts.shop.service.IBannerService;

/**
 * 用户首页轮播图片Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class BannerServiceImpl implements IBannerService 
{
    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 查询用户首页轮播图片
     * 
     * @param id 用户首页轮播图片主键
     * @return 用户首页轮播图片
     */
    @Override
    public Banner selectBannerById(Long id)
    {
        return bannerMapper.selectBannerById(id);
    }

    /**
     * 查询用户首页轮播图片列表
     * 
     * @param banner 用户首页轮播图片
     * @return 用户首页轮播图片
     */
    @Override
    public List<Banner> selectBannerList(Banner banner)
    {
        return bannerMapper.selectBannerList(banner);
    }

    /**
     * 新增用户首页轮播图片
     * 
     * @param banner 用户首页轮播图片
     * @return 结果
     */
    @Override
    public int insertBanner(Banner banner)
    {
        banner.setCreateTime(DateUtils.getNowDate());
        return bannerMapper.insertBanner(banner);
    }

    /**
     * 修改用户首页轮播图片
     * 
     * @param banner 用户首页轮播图片
     * @return 结果
     */
    @Override
    public int updateBanner(Banner banner)
    {
        banner.setUpdateTime(DateUtils.getNowDate());
        return bannerMapper.updateBanner(banner);
    }

    /**
     * 批量删除用户首页轮播图片
     * 
     * @param ids 需要删除的用户首页轮播图片主键
     * @return 结果
     */
    @Override
    public int deleteBannerByIds(Long[] ids)
    {
        return bannerMapper.deleteBannerByIds(ids);
    }

    /**
     * 删除用户首页轮播图片信息
     * 
     * @param id 用户首页轮播图片主键
     * @return 结果
     */
    @Override
    public int deleteBannerById(Long id)
    {
        return bannerMapper.deleteBannerById(id);
    }
}
