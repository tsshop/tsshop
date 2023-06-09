package com.ts.shop.service;

import java.util.List;

import com.ts.shop.domain.LiveStoreIncomeLog;

/**
 * 店铺收入记录Service接口
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
public interface ILiveStoreIncomeLogService 
{
    /**
     * 查询店铺收入记录
     * 
     * @param id 店铺收入记录主键
     * @return 店铺收入记录
     */
    public LiveStoreIncomeLog selectLiveStoreIncomeLogById(Long id);

    /**
     * 查询店铺收入记录列表
     * 
     * @param liveStoreIncomeLog 店铺收入记录
     * @return 店铺收入记录集合
     */
    public List<LiveStoreIncomeLog> selectLiveStoreIncomeLogList(LiveStoreIncomeLog liveStoreIncomeLog);

    /**
     * 新增店铺收入记录
     * 
     * @param liveStoreIncomeLog 店铺收入记录
     * @return 结果
     */
    public int insertLiveStoreIncomeLog(LiveStoreIncomeLog liveStoreIncomeLog);

    /**
     * 修改店铺收入记录
     * 
     * @param liveStoreIncomeLog 店铺收入记录
     * @return 结果
     */
    public int updateLiveStoreIncomeLog(LiveStoreIncomeLog liveStoreIncomeLog);

    /**
     * 批量删除店铺收入记录
     * 
     * @param ids 需要删除的店铺收入记录主键集合
     * @return 结果
     */
    public int deleteLiveStoreIncomeLogByIds(Long[] ids);

    /**
     * 删除店铺收入记录信息
     * 
     * @param id 店铺收入记录主键
     * @return 结果
     */
    public int deleteLiveStoreIncomeLogById(Long id);
}
