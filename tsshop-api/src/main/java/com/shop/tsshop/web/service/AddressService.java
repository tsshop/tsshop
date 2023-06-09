package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.Address;
import com.shop.tsshop.web.model.dto.AddressDto;

/**
 * <p>
 * 用户收货地址表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
public interface AddressService extends IService<Address> {

    /**
     * 创建地址
     * @param address        地址信息
     */
    void create(Address address);

    /**
     * 更新地址
     * @param addressDto     地址信息
     */
    void updateAddress(AddressDto addressDto);
}
