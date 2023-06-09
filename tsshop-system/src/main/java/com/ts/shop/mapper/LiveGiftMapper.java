package com.ts.shop.mapper;

import java.util.List;
import com.ts.shop.domain.LiveGift;

/**
 * 直播礼物Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
public interface LiveGiftMapper 
{
    /**
     * 查询直播礼物
     * 
     * @param id 直播礼物主键
     * @return 直播礼物
     */
    public LiveGift selectLiveGiftById(Long id);

    /**
     * 查询直播礼物列表
     * 
     * @param liveGift 直播礼物
     * @return 直播礼物集合
     */
    public List<LiveGift> selectLiveGiftList(LiveGift liveGift);

    /**
     * 新增直播礼物
     * 
     * @param liveGift 直播礼物
     * @return 结果
     */
    public int insertLiveGift(LiveGift liveGift);

    /**
     * 修改直播礼物
     * 
     * @param liveGift 直播礼物
     * @return 结果
     */
    public int updateLiveGift(LiveGift liveGift);

    /**
     * 删除直播礼物
     * 
     * @param id 直播礼物主键
     * @return 结果
     */
    public int deleteLiveGiftById(Long id);

    /**
     * 批量删除直播礼物
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLiveGiftByIds(Long[] ids);
}
