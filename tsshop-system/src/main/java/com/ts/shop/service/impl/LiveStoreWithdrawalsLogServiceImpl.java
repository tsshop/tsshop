package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.LiveStoreWithdrawalsLog;
import com.ts.shop.mapper.LiveStoreWithdrawalsLogMapper;
import com.ts.shop.service.ILiveStoreWithdrawalsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 店铺提现记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@Service
public class LiveStoreWithdrawalsLogServiceImpl implements ILiveStoreWithdrawalsLogService
{
    @Autowired
    private LiveStoreWithdrawalsLogMapper liveStoreWithdrawalsLogMapper;

    /**
     * 查询店铺提现记录
     * 
     * @param id 店铺提现记录主键
     * @return 店铺提现记录
     */
    @Override
    public LiveStoreWithdrawalsLog selectLiveStoreWithdrawalsLogById(Long id)
    {
        return liveStoreWithdrawalsLogMapper.selectLiveStoreWithdrawalsLogById(id);
    }

    /**
     * 查询店铺提现记录列表
     * 
     * @param liveStoreWithdrawalsLog 店铺提现记录
     * @return 店铺提现记录
     */
    @Override
    public List<LiveStoreWithdrawalsLog> selectLiveStoreWithdrawalsLogList(LiveStoreWithdrawalsLog liveStoreWithdrawalsLog)
    {
        return liveStoreWithdrawalsLogMapper.selectLiveStoreWithdrawalsLogList(liveStoreWithdrawalsLog);
    }

    /**
     * 新增店铺提现记录
     * 
     * @param liveStoreWithdrawalsLog 店铺提现记录
     * @return 结果
     */
    @Override
    public int insertLiveStoreWithdrawalsLog(LiveStoreWithdrawalsLog liveStoreWithdrawalsLog)
    {
        liveStoreWithdrawalsLog.setCreateTime(DateUtils.getNowDate());
        return liveStoreWithdrawalsLogMapper.insertLiveStoreWithdrawalsLog(liveStoreWithdrawalsLog);
    }

    /**
     * 修改店铺提现记录
     * 
     * @param liveStoreWithdrawalsLog 店铺提现记录
     * @return 结果
     */
    @Override
    public int updateLiveStoreWithdrawalsLog(LiveStoreWithdrawalsLog liveStoreWithdrawalsLog)
    {
        liveStoreWithdrawalsLog.setUpdateTime(DateUtils.getNowDate());
        return liveStoreWithdrawalsLogMapper.updateLiveStoreWithdrawalsLog(liveStoreWithdrawalsLog);
    }

    /**
     * 批量删除店铺提现记录
     * 
     * @param ids 需要删除的店铺提现记录主键
     * @return 结果
     */
    @Override
    public int deleteLiveStoreWithdrawalsLogByIds(Long[] ids)
    {
        return liveStoreWithdrawalsLogMapper.deleteLiveStoreWithdrawalsLogByIds(ids);
    }

    /**
     * 删除店铺提现记录信息
     * 
     * @param id 店铺提现记录主键
     * @return 结果
     */
    @Override
    public int deleteLiveStoreWithdrawalsLogById(Long id)
    {
        return liveStoreWithdrawalsLogMapper.deleteLiveStoreWithdrawalsLogById(id);
    }
}
