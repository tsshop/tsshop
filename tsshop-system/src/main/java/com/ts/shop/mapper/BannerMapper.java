package com.ts.shop.mapper;

import java.util.List;
import com.ts.shop.domain.Banner;

/**
 * 用户首页轮播图片Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface BannerMapper 
{
    /**
     * 查询用户首页轮播图片
     * 
     * @param id 用户首页轮播图片主键
     * @return 用户首页轮播图片
     */
    public Banner selectBannerById(Long id);

    /**
     * 查询用户首页轮播图片列表
     * 
     * @param banner 用户首页轮播图片
     * @return 用户首页轮播图片集合
     */
    public List<Banner> selectBannerList(Banner banner);

    /**
     * 新增用户首页轮播图片
     * 
     * @param banner 用户首页轮播图片
     * @return 结果
     */
    public int insertBanner(Banner banner);

    /**
     * 修改用户首页轮播图片
     * 
     * @param banner 用户首页轮播图片
     * @return 结果
     */
    public int updateBanner(Banner banner);

    /**
     * 删除用户首页轮播图片
     * 
     * @param id 用户首页轮播图片主键
     * @return 结果
     */
    public int deleteBannerById(Long id);

    /**
     * 批量删除用户首页轮播图片
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBannerByIds(Long[] ids);
}
