package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.StoreCollectMapper;
import com.ts.shop.domain.StoreCollect;
import com.ts.shop.service.IStoreCollectService;

/**
 * 店铺收藏Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class StoreCollectServiceImpl implements IStoreCollectService 
{
    @Autowired
    private StoreCollectMapper storeCollectMapper;

    /**
     * 查询店铺收藏
     * 
     * @param id 店铺收藏主键
     * @return 店铺收藏
     */
    @Override
    public StoreCollect selectStoreCollectById(Long id)
    {
        return storeCollectMapper.selectStoreCollectById(id);
    }

    /**
     * 查询店铺收藏列表
     * 
     * @param storeCollect 店铺收藏
     * @return 店铺收藏
     */
    @Override
    public List<StoreCollect> selectStoreCollectList(StoreCollect storeCollect)
    {
        return storeCollectMapper.selectStoreCollectList(storeCollect);
    }

    /**
     * 新增店铺收藏
     * 
     * @param storeCollect 店铺收藏
     * @return 结果
     */
    @Override
    public int insertStoreCollect(StoreCollect storeCollect)
    {
        storeCollect.setCreateTime(DateUtils.getNowDate());
        return storeCollectMapper.insertStoreCollect(storeCollect);
    }

    /**
     * 修改店铺收藏
     * 
     * @param storeCollect 店铺收藏
     * @return 结果
     */
    @Override
    public int updateStoreCollect(StoreCollect storeCollect)
    {
        storeCollect.setUpdateTime(DateUtils.getNowDate());
        return storeCollectMapper.updateStoreCollect(storeCollect);
    }

    /**
     * 批量删除店铺收藏
     * 
     * @param ids 需要删除的店铺收藏主键
     * @return 结果
     */
    @Override
    public int deleteStoreCollectByIds(Long[] ids)
    {
        return storeCollectMapper.deleteStoreCollectByIds(ids);
    }

    /**
     * 删除店铺收藏信息
     * 
     * @param id 店铺收藏主键
     * @return 结果
     */
    @Override
    public int deleteStoreCollectById(Long id)
    {
        return storeCollectMapper.deleteStoreCollectById(id);
    }
}
