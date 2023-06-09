package com.ts.shop.service.impl;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.LiveStore;
import com.ts.shop.domain.LiveStoreApply;
import com.ts.shop.domain.vo.LiveStoreInfoVo;
import com.ts.shop.enmus.NumberEnmus;
import com.ts.shop.mapper.LiveStoreApplyMapper;
import com.ts.shop.mapper.LiveStoreMapper;
import com.ts.shop.service.ILiveStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 直播小店Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
@Service
public class LiveStoreServiceImpl implements ILiveStoreService
{
    @Autowired
    private LiveStoreMapper liveStoreMapper;

    @Autowired
    private LiveStoreApplyMapper liveStoreApplyMapper;

    /**
     * 查询直播小店
     * 
     * @param id 直播小店主键
     * @return 直播小店
     */
    @Override
    public LiveStore selectLiveStoreById(Long id)
    {
        return liveStoreMapper.selectLiveStoreById(id);
    }

    /**
     * 查询直播小店列表
     * 
     * @param liveStore 直播小店
     * @return 直播小店
     */
    @Override
    public List<LiveStore> selectLiveStoreList(LiveStore liveStore)
    {
        return liveStoreMapper.selectLiveStoreList(liveStore);
    }

    /**
     * 新增直播小店
     * 
     * @param liveStore 直播小店
     * @return 结果
     */
    @Override
    public int insertLiveStore(LiveStore liveStore)
    {
        liveStore.setCreateTime(DateUtils.getNowDate());
        return liveStoreMapper.insertLiveStore(liveStore);
    }

    /**
     * 修改直播小店
     * 
     * @param liveStore 直播小店
     * @return 结果
     */
    @Override
    public int updateLiveStore(LiveStore liveStore)
    {
        liveStore.setUpdateTime(DateUtils.getNowDate());
        return liveStoreMapper.updateLiveStore(liveStore);
    }

    /**
     * 批量删除直播小店
     * 
     * @param ids 需要删除的直播小店主键
     * @return 结果
     */
    @Override
    public int deleteLiveStoreByIds(Long[] ids)
    {
        return liveStoreMapper.deleteLiveStoreByIds(ids);
    }

    /**
     * 删除直播小店信息
     * 
     * @param id 直播小店主键
     * @return 结果
     */
    @Override
    public int deleteLiveStoreById(Long id)
    {
        return liveStoreMapper.deleteLiveStoreById(id);
    }

    @Override
    public AjaxResult liveStoreInfo(Long liveStoreId) {
        LiveStore liveStore = liveStoreMapper.selectLiveStoreById(liveStoreId);
        if (ObjectUtil.isNull(liveStore)){
            return AjaxResult.error("店铺信息不存在");
        }
        LiveStoreApply liveStoreApply = liveStoreApplyMapper.selectOne(new LambdaQueryWrapper<LiveStoreApply>().eq(LiveStoreApply::getUserId, liveStore.getUserId()).eq(LiveStoreApply::getAuditStatus, NumberEnmus.ONE.getNumber()));
        LiveStoreInfoVo liveStoreInfoVo = new LiveStoreInfoVo();
        BeanUtil.copyProperties(liveStore,liveStoreInfoVo);
        BeanUtil.copyProperties(liveStoreApply,liveStoreInfoVo);
        return AjaxResult.success(liveStoreInfoVo);
    }
}
