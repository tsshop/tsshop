package com.ts.shop.mapper;

import java.util.List;

import com.ts.shop.domain.LiveStoreWithdrawalsConfig;

/**
 * 店铺提现配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
public interface LiveStoreWithdrawalsConfigMapper 
{
    /**
     * 查询店铺提现配置
     * 
     * @param id 店铺提现配置主键
     * @return 店铺提现配置
     */
    public LiveStoreWithdrawalsConfig selectLiveStoreWithdrawalsConfigById(Long id);

    /**
     * 查询店铺提现配置列表
     * 
     * @param liveStoreWithdrawalsConfig 店铺提现配置
     * @return 店铺提现配置集合
     */
    public List<LiveStoreWithdrawalsConfig> selectLiveStoreWithdrawalsConfigList(LiveStoreWithdrawalsConfig liveStoreWithdrawalsConfig);

    /**
     * 新增店铺提现配置
     * 
     * @param liveStoreWithdrawalsConfig 店铺提现配置
     * @return 结果
     */
    public int insertLiveStoreWithdrawalsConfig(LiveStoreWithdrawalsConfig liveStoreWithdrawalsConfig);

    /**
     * 修改店铺提现配置
     * 
     * @param liveStoreWithdrawalsConfig 店铺提现配置
     * @return 结果
     */
    public int updateLiveStoreWithdrawalsConfig(LiveStoreWithdrawalsConfig liveStoreWithdrawalsConfig);

    /**
     * 删除店铺提现配置
     * 
     * @param id 店铺提现配置主键
     * @return 结果
     */
    public int deleteLiveStoreWithdrawalsConfigById(Long id);

    /**
     * 批量删除店铺提现配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLiveStoreWithdrawalsConfigByIds(Long[] ids);
}
