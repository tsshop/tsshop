package com.ts.shop.mapper;

import java.util.List;
import com.ts.shop.domain.Address;

/**
 * 用户收货地址Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface AddressMapper 
{
    /**
     * 查询用户收货地址
     * 
     * @param id 用户收货地址主键
     * @return 用户收货地址
     */
    public Address selectAddressById(Long id);

    /**
     * 查询用户收货地址列表
     * 
     * @param address 用户收货地址
     * @return 用户收货地址集合
     */
    public List<Address> selectAddressList(Address address);

    /**
     * 新增用户收货地址
     * 
     * @param address 用户收货地址
     * @return 结果
     */
    public int insertAddress(Address address);

    /**
     * 修改用户收货地址
     * 
     * @param address 用户收货地址
     * @return 结果
     */
    public int updateAddress(Address address);

    /**
     * 删除用户收货地址
     * 
     * @param id 用户收货地址主键
     * @return 结果
     */
    public int deleteAddressById(Long id);

    /**
     * 批量删除用户收货地址
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAddressByIds(Long[] ids);
}
