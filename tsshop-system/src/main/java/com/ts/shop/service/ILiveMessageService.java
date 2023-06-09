package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.LiveMessage;

/**
 * 弹幕Service接口
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
public interface ILiveMessageService 
{
    /**
     * 查询弹幕
     * 
     * @param id 弹幕主键
     * @return 弹幕
     */
    public LiveMessage selectLiveMessageById(Long id);

    /**
     * 查询弹幕列表
     * 
     * @param liveMessage 弹幕
     * @return 弹幕集合
     */
    public List<LiveMessage> selectLiveMessageList(LiveMessage liveMessage);

    /**
     * 新增弹幕
     * 
     * @param liveMessage 弹幕
     * @return 结果
     */
    public int insertLiveMessage(LiveMessage liveMessage);

    /**
     * 修改弹幕
     * 
     * @param liveMessage 弹幕
     * @return 结果
     */
    public int updateLiveMessage(LiveMessage liveMessage);

    /**
     * 批量删除弹幕
     * 
     * @param ids 需要删除的弹幕主键集合
     * @return 结果
     */
    public int deleteLiveMessageByIds(Long[] ids);

    /**
     * 删除弹幕信息
     * 
     * @param id 弹幕主键
     * @return 结果
     */
    public int deleteLiveMessageById(Long id);
}
