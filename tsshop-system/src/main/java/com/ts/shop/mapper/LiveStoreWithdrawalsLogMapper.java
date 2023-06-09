package com.ts.shop.mapper;

import java.util.List;

import com.ts.shop.domain.LiveStoreWithdrawalsLog;

/**
 * 店铺提现记录Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
public interface LiveStoreWithdrawalsLogMapper 
{
    /**
     * 查询店铺提现记录
     * 
     * @param id 店铺提现记录主键
     * @return 店铺提现记录
     */
    public LiveStoreWithdrawalsLog selectLiveStoreWithdrawalsLogById(Long id);

    /**
     * 查询店铺提现记录列表
     * 
     * @param liveStoreWithdrawalsLog 店铺提现记录
     * @return 店铺提现记录集合
     */
    public List<LiveStoreWithdrawalsLog> selectLiveStoreWithdrawalsLogList(LiveStoreWithdrawalsLog liveStoreWithdrawalsLog);

    /**
     * 新增店铺提现记录
     * 
     * @param liveStoreWithdrawalsLog 店铺提现记录
     * @return 结果
     */
    public int insertLiveStoreWithdrawalsLog(LiveStoreWithdrawalsLog liveStoreWithdrawalsLog);

    /**
     * 修改店铺提现记录
     * 
     * @param liveStoreWithdrawalsLog 店铺提现记录
     * @return 结果
     */
    public int updateLiveStoreWithdrawalsLog(LiveStoreWithdrawalsLog liveStoreWithdrawalsLog);

    /**
     * 删除店铺提现记录
     * 
     * @param id 店铺提现记录主键
     * @return 结果
     */
    public int deleteLiveStoreWithdrawalsLogById(Long id);

    /**
     * 批量删除店铺提现记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLiveStoreWithdrawalsLogByIds(Long[] ids);
}
