package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.LiveMessageMapper;
import com.ts.shop.domain.LiveMessage;
import com.ts.shop.service.ILiveMessageService;

/**
 * 弹幕Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-05
 */
@Service
public class LiveMessageServiceImpl implements ILiveMessageService 
{
    @Autowired
    private LiveMessageMapper liveMessageMapper;

    /**
     * 查询弹幕
     * 
     * @param id 弹幕主键
     * @return 弹幕
     */
    @Override
    public LiveMessage selectLiveMessageById(Long id)
    {
        return liveMessageMapper.selectLiveMessageById(id);
    }

    /**
     * 查询弹幕列表
     * 
     * @param liveMessage 弹幕
     * @return 弹幕
     */
    @Override
    public List<LiveMessage> selectLiveMessageList(LiveMessage liveMessage)
    {
        return liveMessageMapper.selectLiveMessageList(liveMessage);
    }

    /**
     * 新增弹幕
     * 
     * @param liveMessage 弹幕
     * @return 结果
     */
    @Override
    public int insertLiveMessage(LiveMessage liveMessage)
    {
        liveMessage.setCreateTime(DateUtils.getNowDate());
        return liveMessageMapper.insertLiveMessage(liveMessage);
    }

    /**
     * 修改弹幕
     * 
     * @param liveMessage 弹幕
     * @return 结果
     */
    @Override
    public int updateLiveMessage(LiveMessage liveMessage)
    {
        liveMessage.setUpdateTime(DateUtils.getNowDate());
        return liveMessageMapper.updateLiveMessage(liveMessage);
    }

    /**
     * 批量删除弹幕
     * 
     * @param ids 需要删除的弹幕主键
     * @return 结果
     */
    @Override
    public int deleteLiveMessageByIds(Long[] ids)
    {
        return liveMessageMapper.deleteLiveMessageByIds(ids);
    }

    /**
     * 删除弹幕信息
     * 
     * @param id 弹幕主键
     * @return 结果
     */
    @Override
    public int deleteLiveMessageById(Long id)
    {
        return liveMessageMapper.deleteLiveMessageById(id);
    }
}
