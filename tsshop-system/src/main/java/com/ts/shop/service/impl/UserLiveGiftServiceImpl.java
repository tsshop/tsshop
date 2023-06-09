package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.UserLiveGift;
import com.ts.shop.mapper.UserLiveGiftMapper;
import com.ts.shop.service.IUserLiveGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户礼物Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
@Service
public class UserLiveGiftServiceImpl implements IUserLiveGiftService
{
    @Autowired
    private UserLiveGiftMapper userLiveGiftMapper;

    /**
     * 查询用户礼物
     *
     * @param id 用户礼物主键
     * @return 用户礼物
     */
    @Override
    public UserLiveGift selectUserLiveGiftById(Long id)
    {
        return userLiveGiftMapper.selectUserLiveGiftById(id);
    }

    /**
     * 查询用户礼物列表
     * 
     * @param userLiveGift 用户礼物
     * @return 用户礼物
     */
    @Override
    public List<UserLiveGift> selectUserLiveGiftList(UserLiveGift userLiveGift)
    {
        return userLiveGiftMapper.selectUserLiveGiftList(userLiveGift);
    }

    /**
     * 新增用户礼物
     * 
     * @param userLiveGift 用户礼物
     * @return 结果
     */
    @Override
    public int insertUserLiveGift(UserLiveGift userLiveGift)
    {
        userLiveGift.setCreateTime(DateUtils.getNowDate());
        return userLiveGiftMapper.insertUserLiveGift(userLiveGift);
    }

    /**
     * 修改用户礼物
     * 
     * @param userLiveGift 用户礼物
     * @return 结果
     */
    @Override
    public int updateUserLiveGift(UserLiveGift userLiveGift)
    {
        userLiveGift.setUpdateTime(DateUtils.getNowDate());
        return userLiveGiftMapper.updateUserLiveGift(userLiveGift);
    }

    /**
     * 批量删除用户礼物
     * 
     * @param ids 需要删除的用户礼物主键
     * @return 结果
     */
    @Override
    public int deleteUserLiveGiftByIds(Long[] ids)
    {
        return userLiveGiftMapper.deleteUserLiveGiftByIds(ids);
    }

    /**
     * 删除用户礼物信息
     * 
     * @param id 用户礼物主键
     * @return 结果
     */
    @Override
    public int deleteUserLiveGiftById(Long id)
    {
        return userLiveGiftMapper.deleteUserLiveGiftById(id);
    }
}
