package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.LiveGift;
import com.ts.shop.mapper.LiveGiftMapper;
import com.ts.shop.service.ILiveGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 直播礼物Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@Service
public class LiveGiftServiceImpl implements ILiveGiftService
{
    @Autowired
    private LiveGiftMapper liveGiftMapper;

    /**
     * 查询直播礼物
     * 
     * @param id 直播礼物主键
     * @return 直播礼物
     */
    @Override
    public LiveGift selectLiveGiftById(Long id)
    {
        return liveGiftMapper.selectLiveGiftById(id);
    }

    /**
     * 查询直播礼物列表
     * 
     * @param liveGift 直播礼物
     * @return 直播礼物
     */
    @Override
    public List<LiveGift> selectLiveGiftList(LiveGift liveGift)
    {
        return liveGiftMapper.selectLiveGiftList(liveGift);
    }

    /**
     * 新增直播礼物
     * 
     * @param liveGift 直播礼物
     * @return 结果
     */
    @Override
    public int insertLiveGift(LiveGift liveGift)
    {
        liveGift.setCreateTime(DateUtils.getNowDate());
        return liveGiftMapper.insertLiveGift(liveGift);
    }

    /**
     * 修改直播礼物
     * 
     * @param liveGift 直播礼物
     * @return 结果
     */
    @Override
    public int updateLiveGift(LiveGift liveGift)
    {
        liveGift.setUpdateTime(DateUtils.getNowDate());
        return liveGiftMapper.updateLiveGift(liveGift);
    }

    /**
     * 批量删除直播礼物
     * 
     * @param ids 需要删除的直播礼物主键
     * @return 结果
     */
    @Override
    public int deleteLiveGiftByIds(Long[] ids)
    {
        return liveGiftMapper.deleteLiveGiftByIds(ids);
    }

    /**
     * 删除直播礼物信息
     * 
     * @param id 直播礼物主键
     * @return 结果
     */
    @Override
    public int deleteLiveGiftById(Long id)
    {
        return liveGiftMapper.deleteLiveGiftById(id);
    }
}
