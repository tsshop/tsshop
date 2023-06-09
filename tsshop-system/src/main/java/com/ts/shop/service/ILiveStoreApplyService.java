package com.ts.shop.service;

import java.util.List;

import com.ts.shop.domain.LiveStoreApply;

/**
 * 直播小店申请Service接口
 * 
 * @author ruoyi
 * @date 2023-05-24
 */
public interface ILiveStoreApplyService 
{
    /**
     * 查询直播小店申请
     * 
     * @param id 直播小店申请主键
     * @return 直播小店申请
     */
    public LiveStoreApply selectLiveStoreApplyById(Long id);

    /**
     * 查询直播小店申请列表
     * 
     * @param liveStoreApply 直播小店申请
     * @return 直播小店申请集合
     */
    public List<LiveStoreApply> selectLiveStoreApplyList(LiveStoreApply liveStoreApply);

    /**
     * 新增直播小店申请
     * 
     * @param liveStoreApply 直播小店申请
     * @return 结果
     */
    public int insertLiveStoreApply(LiveStoreApply liveStoreApply);

    /**
     * 修改直播小店申请
     * 
     * @param liveStoreApply 直播小店申请
     * @return 结果
     */
    public int updateLiveStoreApply(LiveStoreApply liveStoreApply);

    /**
     * 批量删除直播小店申请
     * 
     * @param ids 需要删除的直播小店申请主键集合
     * @return 结果
     */
    public int deleteLiveStoreApplyByIds(Long[] ids);

    /**
     * 删除直播小店申请信息
     * 
     * @param id 直播小店申请主键
     * @return 结果
     */
    public int deleteLiveStoreApplyById(Long id);
}
