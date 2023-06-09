package com.ts.shop.service;

import java.util.List;

import com.ts.shop.domain.UserLiveGift;

/**
 * 用户礼物Service接口
 * 
 * @author ruoyi
 * @date 2023-05-31
 */
public interface IUserLiveGiftService 
{
    /**
     * 查询用户礼物
     * 
     * @param id 用户礼物主键
     * @return 用户礼物
     */
    public UserLiveGift selectUserLiveGiftById(Long id);

    /**
     * 查询用户礼物列表
     * 
     * @param userLiveGift 用户礼物
     * @return 用户礼物集合
     */
    public List<UserLiveGift> selectUserLiveGiftList(UserLiveGift userLiveGift);

    /**
     * 新增用户礼物
     * 
     * @param userLiveGift 用户礼物
     * @return 结果
     */
    public int insertUserLiveGift(UserLiveGift userLiveGift);

    /**
     * 修改用户礼物
     * 
     * @param userLiveGift 用户礼物
     * @return 结果
     */
    public int updateUserLiveGift(UserLiveGift userLiveGift);

    /**
     * 批量删除用户礼物
     * 
     * @param ids 需要删除的用户礼物主键集合
     * @return 结果
     */
    public int deleteUserLiveGiftByIds(Long[] ids);

    /**
     * 删除用户礼物信息
     * 
     * @param id 用户礼物主键
     * @return 结果
     */
    public int deleteUserLiveGiftById(Long id);
}
