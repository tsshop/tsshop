package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.StoreCollect;

/**
 * 店铺收藏Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IStoreCollectService 
{
    /**
     * 查询店铺收藏
     * 
     * @param id 店铺收藏主键
     * @return 店铺收藏
     */
    public StoreCollect selectStoreCollectById(Long id);

    /**
     * 查询店铺收藏列表
     * 
     * @param storeCollect 店铺收藏
     * @return 店铺收藏集合
     */
    public List<StoreCollect> selectStoreCollectList(StoreCollect storeCollect);

    /**
     * 新增店铺收藏
     * 
     * @param storeCollect 店铺收藏
     * @return 结果
     */
    public int insertStoreCollect(StoreCollect storeCollect);

    /**
     * 修改店铺收藏
     * 
     * @param storeCollect 店铺收藏
     * @return 结果
     */
    public int updateStoreCollect(StoreCollect storeCollect);

    /**
     * 批量删除店铺收藏
     * 
     * @param ids 需要删除的店铺收藏主键集合
     * @return 结果
     */
    public int deleteStoreCollectByIds(Long[] ids);

    /**
     * 删除店铺收藏信息
     * 
     * @param id 店铺收藏主键
     * @return 结果
     */
    public int deleteStoreCollectById(Long id);
}
