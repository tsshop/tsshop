package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.User;

/**
 * 用户Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IUserService 
{
    /**
     * 查询用户
     * 
     * @param id 用户主键
     * @return 用户
     */
    public User selectUserById(Long id);

    /**
     * 查询用户列表
     * 
     * @param user 用户
     * @return 用户集合
     */
    public List<User> selectUserList(User user);

    /**
     * 新增用户
     * 
     * @param user 用户
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 修改用户
     * 
     * @param user 用户
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 批量删除用户
     * 
     * @param ids 需要删除的用户主键集合
     * @return 结果
     */
    public int deleteUserByIds(Long[] ids);

    /**
     * 删除用户信息
     * 
     * @param id 用户主键
     * @return 结果
     */
    public int deleteUserById(Long id);

    Object count(int i);
}
