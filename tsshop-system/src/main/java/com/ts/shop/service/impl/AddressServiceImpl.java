package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.AddressMapper;
import com.ts.shop.domain.Address;
import com.ts.shop.service.IAddressService;

/**
 * 用户收货地址Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class AddressServiceImpl implements IAddressService 
{
    @Autowired
    private AddressMapper addressMapper;

    /**
     * 查询用户收货地址
     * 
     * @param id 用户收货地址主键
     * @return 用户收货地址
     */
    @Override
    public Address selectAddressById(Long id)
    {
        return addressMapper.selectAddressById(id);
    }

    /**
     * 查询用户收货地址列表
     * 
     * @param address 用户收货地址
     * @return 用户收货地址
     */
    @Override
    public List<Address> selectAddressList(Address address)
    {
        return addressMapper.selectAddressList(address);
    }

    /**
     * 新增用户收货地址
     * 
     * @param address 用户收货地址
     * @return 结果
     */
    @Override
    public int insertAddress(Address address)
    {
        address.setCreateTime(DateUtils.getNowDate());
        return addressMapper.insertAddress(address);
    }

    /**
     * 修改用户收货地址
     * 
     * @param address 用户收货地址
     * @return 结果
     */
    @Override
    public int updateAddress(Address address)
    {
        address.setUpdateTime(DateUtils.getNowDate());
        return addressMapper.updateAddress(address);
    }

    /**
     * 批量删除用户收货地址
     * 
     * @param ids 需要删除的用户收货地址主键
     * @return 结果
     */
    @Override
    public int deleteAddressByIds(Long[] ids)
    {
        return addressMapper.deleteAddressByIds(ids);
    }

    /**
     * 删除用户收货地址信息
     * 
     * @param id 用户收货地址主键
     * @return 结果
     */
    @Override
    public int deleteAddressById(Long id)
    {
        return addressMapper.deleteAddressById(id);
    }
}
