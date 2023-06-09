package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.LiveBroadcastMapper;
import com.ts.shop.domain.LiveBroadcast;
import com.ts.shop.service.ILiveBroadcastService;

/**
 * 直播记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
@Service
public class LiveBroadcastServiceImpl implements ILiveBroadcastService 
{
    @Autowired
    private LiveBroadcastMapper liveBroadcastMapper;

    /**
     * 查询直播记录
     * 
     * @param id 直播记录主键
     * @return 直播记录
     */
    @Override
    public LiveBroadcast selectLiveBroadcastById(Long id)
    {
        return liveBroadcastMapper.selectLiveBroadcastById(id);
    }

    /**
     * 查询直播记录列表
     * 
     * @param liveBroadcast 直播记录
     * @return 直播记录
     */
    @Override
    public List<LiveBroadcast> selectLiveBroadcastList(LiveBroadcast liveBroadcast)
    {
        return liveBroadcastMapper.selectLiveBroadcastList(liveBroadcast);
    }

    /**
     * 新增直播记录
     * 
     * @param liveBroadcast 直播记录
     * @return 结果
     */
    @Override
    public int insertLiveBroadcast(LiveBroadcast liveBroadcast)
    {
        liveBroadcast.setCreateTime(DateUtils.getNowDate());
        return liveBroadcastMapper.insertLiveBroadcast(liveBroadcast);
    }

    /**
     * 修改直播记录
     * 
     * @param liveBroadcast 直播记录
     * @return 结果
     */
    @Override
    public int updateLiveBroadcast(LiveBroadcast liveBroadcast)
    {
        liveBroadcast.setUpdateTime(DateUtils.getNowDate());
        return liveBroadcastMapper.updateLiveBroadcast(liveBroadcast);
    }

    /**
     * 批量删除直播记录
     * 
     * @param ids 需要删除的直播记录主键
     * @return 结果
     */
    @Override
    public int deleteLiveBroadcastByIds(Long[] ids)
    {
        return liveBroadcastMapper.deleteLiveBroadcastByIds(ids);
    }

    /**
     * 删除直播记录信息
     * 
     * @param id 直播记录主键
     * @return 结果
     */
    @Override
    public int deleteLiveBroadcastById(Long id)
    {
        return liveBroadcastMapper.deleteLiveBroadcastById(id);
    }
}
