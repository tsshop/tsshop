package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.LiveBroadcast;

/**
 * 直播记录Service接口
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
public interface ILiveBroadcastService 
{
    /**
     * 查询直播记录
     * 
     * @param id 直播记录主键
     * @return 直播记录
     */
    public LiveBroadcast selectLiveBroadcastById(Long id);

    /**
     * 查询直播记录列表
     * 
     * @param liveBroadcast 直播记录
     * @return 直播记录集合
     */
    public List<LiveBroadcast> selectLiveBroadcastList(LiveBroadcast liveBroadcast);

    /**
     * 新增直播记录
     * 
     * @param liveBroadcast 直播记录
     * @return 结果
     */
    public int insertLiveBroadcast(LiveBroadcast liveBroadcast);

    /**
     * 修改直播记录
     * 
     * @param liveBroadcast 直播记录
     * @return 结果
     */
    public int updateLiveBroadcast(LiveBroadcast liveBroadcast);

    /**
     * 批量删除直播记录
     * 
     * @param ids 需要删除的直播记录主键集合
     * @return 结果
     */
    public int deleteLiveBroadcastByIds(Long[] ids);

    /**
     * 删除直播记录信息
     * 
     * @param id 直播记录主键
     * @return 结果
     */
    public int deleteLiveBroadcastById(Long id);
}
