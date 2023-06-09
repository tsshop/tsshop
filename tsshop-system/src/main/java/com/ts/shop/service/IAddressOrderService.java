package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.AddressOrder;
import com.ts.shop.domain.vo.AddressVo;

/**
 * 用户收货地址Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IAddressOrderService
{
    /**
     * 查询用户收货地址
     * 
     * @param id 用户收货地址主键
     * @return 用户收货地址
     */
    public AddressOrder selectAddressOrderById(Long id);

    /**
     * 查询用户收货地址列表
     * 
     * @param addressOrder 用户收货地址
     * @return 用户收货地址集合
     */
    public List<AddressOrder> selectAddressOrderList(AddressOrder addressOrder);

    /**
     * 新增用户收货地址
     * 
     * @param addressOrder 用户收货地址
     * @return 结果
     */
    public int insertAddressOrder(AddressOrder addressOrder);

    /**
     * 修改用户收货地址
     * 
     * @param addressOrder 用户收货地址
     * @return 结果
     */
    public int updateAddressOrder(AddressOrder addressOrder);

    /**
     * 批量删除用户收货地址
     * 
     * @param ids 需要删除的用户收货地址主键集合
     * @return 结果
     */
    public int deleteAddressOrderByIds(Long[] ids);

    /**
     * 删除用户收货地址信息
     * 
     * @param id 用户收货地址主键
     * @return 结果
     */
    public int deleteAddressOrderById(Long id);

    AddressVo selectAddresVoById(Long addressId);
}
