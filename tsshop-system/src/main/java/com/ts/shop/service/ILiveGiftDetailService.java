package com.ts.shop.service;

import java.util.List;

import com.ts.shop.domain.LiveGiftDetail;

/**
 * 直播礼物赠送记录Service接口
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
public interface ILiveGiftDetailService 
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
     * 批量删除直播礼物赠送记录
     * 
     * @param ids 需要删除的直播礼物赠送记录主键集合
     * @return 结果
     */
    public int deleteLiveGiftDetailByIds(Long[] ids);

    /**
     * 删除直播礼物赠送记录信息
     * 
     * @param id 直播礼物赠送记录主键
     * @return 结果
     */
    public int deleteLiveGiftDetailById(Long id);
}
