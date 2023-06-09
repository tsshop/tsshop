package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.ReturnAddress;

/**
 * 用户收货地址Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IReturnAddressService 
{
    /**
     * 查询用户收货地址
     * 
     * @param id 用户收货地址主键
     * @return 用户收货地址
     */
    public ReturnAddress selectReturnAddressById(Long id);

    /**
     * 查询用户收货地址列表
     * 
     * @param returnAddress 用户收货地址
     * @return 用户收货地址集合
     */
    public List<ReturnAddress> selectReturnAddressList(ReturnAddress returnAddress);

    /**
     * 新增用户收货地址
     * 
     * @param returnAddress 用户收货地址
     * @return 结果
     */
    public int insertReturnAddress(ReturnAddress returnAddress);

    /**
     * 修改用户收货地址
     * 
     * @param returnAddress 用户收货地址
     * @return 结果
     */
    public int updateReturnAddress(ReturnAddress returnAddress);

    /**
     * 批量删除用户收货地址
     * 
     * @param ids 需要删除的用户收货地址主键集合
     * @return 结果
     */
    public int deleteReturnAddressByIds(Long[] ids);

    /**
     * 删除用户收货地址信息
     * 
     * @param id 用户收货地址主键
     * @return 结果
     */
    public int deleteReturnAddressById(Long id);
}
