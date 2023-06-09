package com.shop.tsshop.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.exception.ApiCode;
import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.web.model.domain.Address;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.AddressDto;
import com.shop.tsshop.web.model.vo.AddressVo;
import com.shop.tsshop.web.model.vo.AreaVo;
import com.shop.tsshop.web.service.AddressService;
import com.shop.tsshop.web.service.AreaService;
import com.shop.tsshop.web.service.RedisService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户收货地址表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AreaService areaService;

    @GetMapping("district")
    //"用户端：查询地址省市县")
    //("查询地址省市县")
    public ApiResult<Object> getDistrict() {
        List<AreaVo> list = areaService.getDistrict();
        return ApiResult.ok(list);
    }

    @GetMapping("/getList")
    //("查询地址信息")
    public ApiResult<List<Address>> getAddress(HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        List<Address> addressList = addressService.list(new LambdaQueryWrapper<Address>()
                .eq(Address::getUserId, user.getId())
                .eq(Address::getDeleted, 0)
//                .orderByDesc(Address::getDefaultCargo)
                .orderByDesc(Address::getCreateTime));
        return ApiResult.ok(addressList);
    }

    @PostMapping("/create")
    //("新增地址信息不穿id")
    public ApiResult<String> create(@RequestBody Address address,HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        if (address.getName() == null || address.getName().length() >= 12) {
            throw new MyException(ApiCode.NONAME.getCode(), "请填写12字以内的姓名");
        }
        if (address.getPhone() == null || address.getPhone().length() != 11) {
            throw new MyException(ApiCode.NOPHONE.getCode(), "请填写合法的手机号");
        }
        address.setUserId(user.getId());

        addressService.create(address);
        return ApiResult.ok();
    }

    @PostMapping("update")
    //("修改地址信息id必穿")
    public ApiResult<String> update(@RequestBody AddressDto addressDto,HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        Address address=addressService.getById(addressDto.getId());
        if (address== null || !Objects.equals(user.getId(), address.getUserId())) {
            return ApiResult.fail("没有该地址");
        }
        if (addressDto.getName() == null || addressDto.getName().length() >= 12) {
            throw new MyException(ApiCode.NONAME.getCode(), "请填写12字以内的姓名");
        }
        if (addressDto.getPhone() == null || addressDto.getPhone().length() != 11) {
            throw new MyException(ApiCode.NOPHONE.getCode(), "请填写合法的手机号");
        }


        addressService.updateAddress(addressDto);
        return ApiResult.ok();
    }

    //("删除地址信息只穿id")

    @PostMapping("delete")
    public ApiResult<String> deleteAll(@RequestBody AddressDto dto,HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        Address address = addressService.getById(dto.getId());
        if (address== null || !Objects.equals(user.getId(), address.getUserId())) {
            return ApiResult.fail("地址不存在！");
        }
        
        //修改地址状态为删除状态，而非正式删除
        address.setDeleted(1);
        address.setDefaultCargo(0);
        addressService.updateById(address);
        return ApiResult.ok();
    }

    //("设置默认收货地址")

    @PostMapping("setDefault")
    public ApiResult<String> setDefaultAddress(@RequestBody AddressDto dto,HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        Address address = addressService.getById(dto.getId());
        if (address== null || !Objects.equals(user.getId(), address.getUserId())) {
            return ApiResult.fail("地址不存在！");
        }
        
        //设置默认收货地址
        //清除其他地址的默认地址
        addressService.update(new LambdaUpdateWrapper<Address>()
                .eq(Address::getUserId,user.getId())
                .set(Address::getDefaultCargo, 0));
        //设置该地址为默认
        addressService.update(new LambdaUpdateWrapper<Address>()
                .eq(Address::getId, dto.getId())
                .set(Address::getDefaultCargo, 1));
        return ApiResult.ok();
    }

    //("获取用户默认收货地址")

    @GetMapping("getDefault")
    public ApiResult<AddressVo> getDefault(HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        Address address = addressService.getOne(new LambdaQueryWrapper<Address>().eq(Address::getUserId, user.getId()).eq(Address::getDeleted, 0).eq(Address::getDefaultCargo, 1));
        return ApiResult.ok(mapperFacade.map(address, AddressVo.class));
    }
}
