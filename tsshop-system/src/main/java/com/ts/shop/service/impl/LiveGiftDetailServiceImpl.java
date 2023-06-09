package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.LiveGiftDetail;
import com.ts.shop.mapper.LiveGiftDetailMapper;
import com.ts.shop.service.ILiveGiftDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 直播礼物赠送记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
@Service
public class LiveGiftDetailServiceImpl implements ILiveGiftDetailService
{
    @Autowired
    private LiveGiftDetailMapper liveGiftDetailMapper;

    /**
     * 查询直播礼物赠送记录
     *
     * @param id 直播礼物赠送记录主键
     * @return 直播礼物赠送记录
     */
    @Override
    public LiveGiftDetail selectLiveGiftDetailById(Long id)
    {
        return liveGiftDetailMapper.selectLiveGiftDetailById(id);
    }

    /**
     * 查询直播礼物赠送记录列表
     * 
     * @param liveGiftDetail 直播礼物赠送记录
     * @return 直播礼物赠送记录
     */
    @Override
    public List<LiveGiftDetail> selectLiveGiftDetailList(LiveGiftDetail liveGiftDetail)
    {
        return liveGiftDetailMapper.selectLiveGiftDetailList(liveGiftDetail);
    }

    /**
     * 新增直播礼物赠送记录
     * 
     * @param liveGiftDetail 直播礼物赠送记录
     * @return 结果
     */
    @Override
    public int insertLiveGiftDetail(LiveGiftDetail liveGiftDetail)
    {
        liveGiftDetail.setCreateTime(DateUtils.getNowDate());
        return liveGiftDetailMapper.insertLiveGiftDetail(liveGiftDetail);
    }

    /**
     * 修改直播礼物赠送记录
     * 
     * @param liveGiftDetail 直播礼物赠送记录
     * @return 结果
     */
    @Override
    public int updateLiveGiftDetail(LiveGiftDetail liveGiftDetail)
    {
        liveGiftDetail.setUpdateTime(DateUtils.getNowDate());
        return liveGiftDetailMapper.updateLiveGiftDetail(liveGiftDetail);
    }

    /**
     * 批量删除直播礼物赠送记录
     * 
     * @param ids 需要删除的直播礼物赠送记录主键
     * @return 结果
     */
    @Override
    public int deleteLiveGiftDetailByIds(Long[] ids)
    {
        return liveGiftDetailMapper.deleteLiveGiftDetailByIds(ids);
    }

    /**
     * 删除直播礼物赠送记录信息
     * 
     * @param id 直播礼物赠送记录主键
     * @return 结果
     */
    @Override
    public int deleteLiveGiftDetailById(Long id)
    {
        return liveGiftDetailMapper.deleteLiveGiftDetailById(id);
    }
}
