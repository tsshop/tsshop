package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.ReturnAddressMapper;
import com.ts.shop.domain.ReturnAddress;
import com.ts.shop.service.IReturnAddressService;

/**
 * 用户收货地址Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class ReturnAddressServiceImpl implements IReturnAddressService 
{
    @Autowired
    private ReturnAddressMapper returnAddressMapper;

    /**
     * 查询用户收货地址
     * 
     * @param id 用户收货地址主键
     * @return 用户收货地址
     */
    @Override
    public ReturnAddress selectReturnAddressById(Long id)
    {
        return returnAddressMapper.selectReturnAddressById(id);
    }

    /**
     * 查询用户收货地址列表
     * 
     * @param returnAddress 用户收货地址
     * @return 用户收货地址
     */
    @Override
    public List<ReturnAddress> selectReturnAddressList(ReturnAddress returnAddress)
    {
        return returnAddressMapper.selectReturnAddressList(returnAddress);
    }

    /**
     * 新增用户收货地址
     * 
     * @param returnAddress 用户收货地址
     * @return 结果
     */
    @Override
    public int insertReturnAddress(ReturnAddress returnAddress)
    {
        returnAddress.setCreateTime(DateUtils.getNowDate());
        return returnAddressMapper.insertReturnAddress(returnAddress);
    }

    /**
     * 修改用户收货地址
     * 
     * @param returnAddress 用户收货地址
     * @return 结果
     */
    @Override
    public int updateReturnAddress(ReturnAddress returnAddress)
    {
        returnAddress.setUpdateTime(DateUtils.getNowDate());
        return returnAddressMapper.updateReturnAddress(returnAddress);
    }

    /**
     * 批量删除用户收货地址
     * 
     * @param ids 需要删除的用户收货地址主键
     * @return 结果
     */
    @Override
    public int deleteReturnAddressByIds(Long[] ids)
    {
        return returnAddressMapper.deleteReturnAddressByIds(ids);
    }

    /**
     * 删除用户收货地址信息
     * 
     * @param id 用户收货地址主键
     * @return 结果
     */
    @Override
    public int deleteReturnAddressById(Long id)
    {
        return returnAddressMapper.deleteReturnAddressById(id);
    }
}
