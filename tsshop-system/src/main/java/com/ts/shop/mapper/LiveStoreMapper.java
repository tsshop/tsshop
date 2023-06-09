package com.ts.shop.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.shop.domain.LiveStore;

/**
 * 直播小店Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
public interface LiveStoreMapper extends BaseMapper<LiveStore>
{
    /**
     * 查询直播小店
     * 
     * @param id 直播小店主键
     * @return 直播小店
     */
    public LiveStore selectLiveStoreById(Long id);

    /**
     * 查询直播小店列表
     * 
     * @param liveStore 直播小店
     * @return 直播小店集合
     */
    public List<LiveStore> selectLiveStoreList(LiveStore liveStore);

    /**
     * 新增直播小店
     * 
     * @param liveStore 直播小店
     * @return 结果
     */
    public int insertLiveStore(LiveStore liveStore);

    /**
     * 修改直播小店
     * 
     * @param liveStore 直播小店
     * @return 结果
     */
    public int updateLiveStore(LiveStore liveStore);

    /**
     * 删除直播小店
     * 
     * @param id 直播小店主键
     * @return 结果
     */
    public int deleteLiveStoreById(Long id);

    /**
     * 批量删除直播小店
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLiveStoreByIds(Long[] ids);
}
