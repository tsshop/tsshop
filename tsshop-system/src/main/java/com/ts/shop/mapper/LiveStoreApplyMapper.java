package com.ts.shop.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.shop.domain.LiveStoreApply;

/**
 * 直播小店申请Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-24
 */
public interface LiveStoreApplyMapper extends BaseMapper<LiveStoreApply>
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
     * 删除直播小店申请
     * 
     * @param id 直播小店申请主键
     * @return 结果
     */
    public int deleteLiveStoreApplyById(Long id);

    /**
     * 批量删除直播小店申请
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLiveStoreApplyByIds(Long[] ids);
}
