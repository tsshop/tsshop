package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.Area;
import com.ts.shop.domain.vo.AddressVo;
import com.ts.shop.mapper.AreaMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.AddressOrderMapper;
import com.ts.shop.domain.AddressOrder;
import com.ts.shop.service.IAddressOrderService;

/**
 * 用户收货地址Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class AddressOrderServiceImpl implements IAddressOrderService 
{
    @Autowired
    private AddressOrderMapper addressOrderMapper;
    @Autowired
    private AreaMapper areaMapper;

    /**
     * 查询用户收货地址
     * 
     * @param id 用户收货地址主键
     * @return 用户收货地址
     */
    @Override
    public AddressOrder selectAddressOrderById(Long id)
    {
        return addressOrderMapper.selectAddressOrderById(id);
    }

    /**
     * 查询用户收货地址列表
     * 
     * @param addressOrder 用户收货地址
     * @return 用户收货地址
     */
    @Override
    public List<AddressOrder> selectAddressOrderList(AddressOrder addressOrder)
    {
        return addressOrderMapper.selectAddressOrderList(addressOrder);
    }

    /**
     * 新增用户收货地址
     * 
     * @param addressOrder 用户收货地址
     * @return 结果
     */
    @Override
    public int insertAddressOrder(AddressOrder addressOrder)
    {
        addressOrder.setCreateTime(DateUtils.getNowDate());
        return addressOrderMapper.insertAddressOrder(addressOrder);
    }

    /**
     * 修改用户收货地址
     * 
     * @param addressOrder 用户收货地址
     * @return 结果
     */
    @Override
    public int updateAddressOrder(AddressOrder addressOrder)
    {
        addressOrder.setUpdateTime(DateUtils.getNowDate());
        return addressOrderMapper.updateAddressOrder(addressOrder);
    }

    /**
     * 批量删除用户收货地址
     * 
     * @param ids 需要删除的用户收货地址主键
     * @return 结果
     */
    @Override
    public int deleteAddressOrderByIds(Long[] ids)
    {
        return addressOrderMapper.deleteAddressOrderByIds(ids);
    }

    /**
     * 删除用户收货地址信息
     * 
     * @param id 用户收货地址主键
     * @return 结果
     */
    @Override
    public int deleteAddressOrderById(Long id)
    {
        return addressOrderMapper.deleteAddressOrderById(id);
    }

    @Override
    public AddressVo selectAddresVoById(Long addressId) {


        AddressOrder addressOrder=addressOrderMapper.selectAddressOrderById(addressId);
        AddressVo addressVo=new AddressVo();
        BeanUtils.copyProperties(addressOrder,addressVo);
        Area pr = areaMapper.selectAreaByAreaId(addressOrder.getProvince());
        Area ci = areaMapper.selectAreaByAreaId(addressOrder.getCity());
        Area ar = areaMapper.selectAreaByAreaId(addressOrder.getArea());
        addressVo.setAreaName(ar.getAreaName());
        addressVo.setProvinceName(pr.getAreaName());
        addressVo.setCityName(ci.getAreaName());
        return addressVo;
    }
}
