package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.LiveStoreIncomeLog;
import com.ts.shop.mapper.LiveStoreIncomeLogMapper;
import com.ts.shop.service.ILiveStoreIncomeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 店铺收入记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
@Service
public class LiveStoreIncomeLogServiceImpl implements ILiveStoreIncomeLogService
{
    @Autowired
    private LiveStoreIncomeLogMapper liveStoreIncomeLogMapper;

    /**
     * 查询店铺收入记录
     * 
     * @param id 店铺收入记录主键
     * @return 店铺收入记录
     */
    @Override
    public LiveStoreIncomeLog selectLiveStoreIncomeLogById(Long id)
    {
        return liveStoreIncomeLogMapper.selectLiveStoreIncomeLogById(id);
    }

    /**
     * 查询店铺收入记录列表
     * 
     * @param liveStoreIncomeLog 店铺收入记录
     * @return 店铺收入记录
     */
    @Override
    public List<LiveStoreIncomeLog> selectLiveStoreIncomeLogList(LiveStoreIncomeLog liveStoreIncomeLog)
    {
        return liveStoreIncomeLogMapper.selectLiveStoreIncomeLogList(liveStoreIncomeLog);
    }

    /**
     * 新增店铺收入记录
     * 
     * @param liveStoreIncomeLog 店铺收入记录
     * @return 结果
     */
    @Override
    public int insertLiveStoreIncomeLog(LiveStoreIncomeLog liveStoreIncomeLog)
    {
        liveStoreIncomeLog.setCreateTime(DateUtils.getNowDate());
        return liveStoreIncomeLogMapper.insertLiveStoreIncomeLog(liveStoreIncomeLog);
    }

    /**
     * 修改店铺收入记录
     * 
     * @param liveStoreIncomeLog 店铺收入记录
     * @return 结果
     */
    @Override
    public int updateLiveStoreIncomeLog(LiveStoreIncomeLog liveStoreIncomeLog)
    {
        liveStoreIncomeLog.setUpdateTime(DateUtils.getNowDate());
        return liveStoreIncomeLogMapper.updateLiveStoreIncomeLog(liveStoreIncomeLog);
    }

    /**
     * 批量删除店铺收入记录
     * 
     * @param ids 需要删除的店铺收入记录主键
     * @return 结果
     */
    @Override
    public int deleteLiveStoreIncomeLogByIds(Long[] ids)
    {
        return liveStoreIncomeLogMapper.deleteLiveStoreIncomeLogByIds(ids);
    }

    /**
     * 删除店铺收入记录信息
     * 
     * @param id 店铺收入记录主键
     * @return 结果
     */
    @Override
    public int deleteLiveStoreIncomeLogById(Long id)
    {
        return liveStoreIncomeLogMapper.deleteLiveStoreIncomeLogById(id);
    }
}
