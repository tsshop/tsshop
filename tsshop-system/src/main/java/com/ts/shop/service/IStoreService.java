package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.Store;

/**
 * 店铺Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IStoreService 
{
    /**
     * 查询店铺
     * 
     * @param id 店铺主键
     * @return 店铺
     */
    public Store selectStoreById(Long id);

    /**
     * 查询店铺列表
     * 
     * @param store 店铺
     * @return 店铺集合
     */
    public List<Store> selectStoreList(Store store);

    /**
     * 新增店铺
     * 
     * @param store 店铺
     * @return 结果
     */
    public int insertStore(Store store);

    /**
     * 修改店铺
     * 
     * @param store 店铺
     * @return 结果
     */
    public int updateStore(Store store);

    /**
     * 批量删除店铺
     * 
     * @param ids 需要删除的店铺主键集合
     * @return 结果
     */
    public int deleteStoreByIds(Long[] ids);

    /**
     * 删除店铺信息
     * 
     * @param id 店铺主键
     * @return 结果
     */
    public int deleteStoreById(Long id);
}
