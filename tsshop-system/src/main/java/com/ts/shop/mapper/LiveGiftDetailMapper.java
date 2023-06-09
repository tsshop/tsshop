package com.ts.shop.mapper;

import java.util.List;

import com.ts.shop.domain.LiveGiftDetail;

/**
 * 直播礼物赠送记录Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
public interface LiveGiftDetailMapper 
{
    /**
     * 查询直播礼物赠送记录
     * 
     * @param id 直播礼物赠送记录主键
     * @return 直播礼物赠送记录
     */
    public LiveGiftDetail selectLiveGiftDetailById(Long id);

    /**
     * 查询直播礼物赠送记录列表
     * 
     * @param liveGiftDetail 直播礼物赠送记录
     * @return 直播礼物赠送记录集合
     */
    public List<LiveGiftDetail> selectLiveGiftDetailList(LiveGiftDetail liveGiftDetail);

    /**
     * 新增直播礼物赠送记录
     * 
     * @param liveGiftDetail 直播礼物赠送记录
     * @return 结果
     */
    public int insertLiveGiftDetail(LiveGiftDetail liveGiftDetail);

    /**
     * 修改直播礼物赠送记录
     * 
     * @param liveGiftDetail 直播礼物赠送记录
     * @return 结果
     */
    public int updateLiveGiftDetail(LiveGiftDetail liveGiftDetail);

    /**
     * 删除直播礼物赠送记录
     * 
     * @param id 直播礼物赠送记录主键
     * @return 结果
     */
    public int deleteLiveGiftDetailById(Long id);

    /**
     * 批量删除直播礼物赠送记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLiveGiftDetailByIds(Long[] ids);
}
