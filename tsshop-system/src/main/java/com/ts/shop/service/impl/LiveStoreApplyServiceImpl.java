package com.ts.shop.service.impl;

import java.util.List;
import java.util.Objects;

import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.LiveStore;
import com.ts.shop.domain.LiveStoreApply;
import com.ts.shop.domain.User;
import com.ts.shop.enmus.NumberEnmus;
import com.ts.shop.mapper.LiveStoreApplyMapper;
import com.ts.shop.mapper.LiveStoreMapper;
import com.ts.shop.mapper.UserMapper;
import com.ts.shop.service.ILiveStoreApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 直播小店申请Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-24
 */
@Service
public class LiveStoreApplyServiceImpl implements ILiveStoreApplyService
{
    @Autowired
    private LiveStoreApplyMapper liveStoreApplyMapper;

    @Autowired
    private LiveStoreMapper liveStoreMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询直播小店申请
     * 
     * @param id 直播小店申请主键
     * @return 直播小店申请
     */
    @Override
    public LiveStoreApply selectLiveStoreApplyById(Long id)
    {
        return liveStoreApplyMapper.selectLiveStoreApplyById(id);
    }

    /**
     * 查询直播小店申请列表
     * 
     * @param liveStoreApply 直播小店申请
     * @return 直播小店申请
     */
    @Override
    public List<LiveStoreApply> selectLiveStoreApplyList(LiveStoreApply liveStoreApply)
    {
        return liveStoreApplyMapper.selectLiveStoreApplyList(liveStoreApply);
    }

    /**
     * 新增直播小店申请
     * 
     * @param liveStoreApply 直播小店申请
     * @return 结果
     */
    @Override
    public int insertLiveStoreApply(LiveStoreApply liveStoreApply)
    {
        liveStoreApply.setCreateTime(DateUtils.getNowDate());
        return liveStoreApplyMapper.insertLiveStoreApply(liveStoreApply);
    }

    /**
     * 修改直播小店申请
     * 
     * @param liveStoreApply 直播小店申请
     * @return 结果
     */
    @Override
    public int updateLiveStoreApply(LiveStoreApply liveStoreApply)
    {
        liveStoreApply.setUpdateTime(DateUtils.getNowDate());
        if (Objects.equals(liveStoreApply.getAuditStatus(), NumberEnmus.ONE.getNumber())){
            // 创建小店
            User user = userMapper.selectUserById(liveStoreApply.getUserId());
            LiveStore liveStore = new LiveStore();
            liveStore.setStoreName(liveStoreApply.getManagerName());
            liveStore.setStoreHeadPortrait(user.getAvatarUrl());
            liveStore.setStorePhone(liveStoreApply.getManagerPhone());
            liveStore.setUserId(user.getId());
            liveStoreMapper.insertLiveStore(liveStore);
        }
        return liveStoreApplyMapper.updateLiveStoreApply(liveStoreApply);
    }

    /**
     * 批量删除直播小店申请
     * 
     * @param ids 需要删除的直播小店申请主键
     * @return 结果
     */
    @Override
    public int deleteLiveStoreApplyByIds(Long[] ids)
    {
        return liveStoreApplyMapper.deleteLiveStoreApplyByIds(ids);
    }

    /**
     * 删除直播小店申请信息
     * 
     * @param id 直播小店申请主键
     * @return 结果
     */
    @Override
    public int deleteLiveStoreApplyById(Long id)
    {
        return liveStoreApplyMapper.deleteLiveStoreApplyById(id);
    }
}
