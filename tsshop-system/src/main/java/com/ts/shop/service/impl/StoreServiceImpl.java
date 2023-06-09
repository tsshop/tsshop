package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.StoreMapper;
import com.ts.shop.domain.Store;
import com.ts.shop.service.IStoreService;

/**
 * 店铺Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class StoreServiceImpl implements IStoreService 
{
    @Autowired
    private StoreMapper storeMapper;

    /**
     * 查询店铺
     * 
     * @param id 店铺主键
     * @return 店铺
     */
    @Override
    public Store selectStoreById(Long id)
    {
        return storeMapper.selectStoreById(id);
    }

    /**
     * 查询店铺列表
     * 
     * @param store 店铺
     * @return 店铺
     */
    @Override
    public List<Store> selectStoreList(Store store)
    {
        return storeMapper.selectStoreList(store);
    }

    /**
     * 新增店铺
     * 
     * @param store 店铺
     * @return 结果
     */
    @Override
    public int insertStore(Store store)
    {
        store.setCreateTime(DateUtils.getNowDate());
        return storeMapper.insertStore(store);
    }

    /**
     * 修改店铺
     * 
     * @param store 店铺
     * @return 结果
     */
    @Override
    public int updateStore(Store store)
    {
        store.setUpdateTime(DateUtils.getNowDate());
        return storeMapper.updateStore(store);
    }

    /**
     * 批量删除店铺
     * 
     * @param ids 需要删除的店铺主键
     * @return 结果
     */
    @Override
    public int deleteStoreByIds(Long[] ids)
    {
        return storeMapper.deleteStoreByIds(ids);
    }

    /**
     * 删除店铺信息
     * 
     * @param id 店铺主键
     * @return 结果
     */
    @Override
    public int deleteStoreById(Long id)
    {
        return storeMapper.deleteStoreById(id);
    }
}
