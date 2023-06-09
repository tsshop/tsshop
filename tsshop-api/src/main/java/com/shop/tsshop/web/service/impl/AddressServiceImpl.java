package com.shop.tsshop.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.shop.tsshop.config.exception.ApiCode;
import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.web.model.domain.Address;
import com.shop.tsshop.web.mapper.AddressMapper;
import com.shop.tsshop.web.model.domain.Area;
import com.shop.tsshop.web.model.dto.AddressDto;
import com.shop.tsshop.web.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.service.AreaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户收货地址表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Autowired
    AreaService areaService;
    @Override
    public void create(Address address) {
        //查询地址数量
        int count = count(new QueryWrapper<Address>().lambda().eq(Address::getDeleted, 0).eq(Address::getUserId, address.getUserId()));
        //地址不得超过5个
        if (5 <= count) {
            throw new MyException(ApiCode.TOO_LONG);
        }
        if (address.getDefaultCargo()==1) {
            //清除其他地址的默认地址
            update(new LambdaUpdateWrapper<Address>()
                    .eq(Address::getUserId, address.getUserId())
                    .set(Address::getDefaultCargo, 0));
        }
        Area pr = areaService.getById(address.getProvince());
        Area ci = areaService.getById(address.getCity());
        Area ar = areaService.getById(address.getArea());
        address.setAreaName(ar.getAreaName());
        address.setProvinceName(pr.getAreaName());
        address.setCityName(ci.getAreaName());

        save(address);
    }

    @Override
    public void updateAddress(AddressDto address) {
        if (address.getDefaultCargo()==1) {

            //清除其他地址的默认地址
            update(new LambdaUpdateWrapper<Address>()
                    .eq(Address::getUserId, address.getUserId())
                    .set(Address::getDefaultCargo, 0));
        }
        Address a= new Address();
        BeanUtils.copyProperties(address,a);

        Area pr = areaService.getById(a.getProvince());
        Area ci = areaService.getById(a.getCity());
        Area ar = areaService.getById(a.getArea());
        a.setAreaName(ar.getAreaName());
        a.setProvinceName(pr.getAreaName());
        a.setCityName(ci.getAreaName());
        updateById(a);
    }
}
