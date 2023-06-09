package com.shop.tsshop.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.web.model.domain.ShopCart;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.CartDto;
import com.shop.tsshop.web.service.OrderService;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 购物车表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@RestController
@RequestMapping("/shopCart")
public class ShopCartController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ShopCartService cartService;

    @GetMapping("/getList")
    public ApiResult<Object> getList(HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        return ApiResult.ok(cartService.getList(user.getId()));
    }
    @GetMapping("/getCount")
    public ApiResult<Object> getCount(HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        return ApiResult.ok(cartService.count(new LambdaQueryWrapper<ShopCart>().eq(ShopCart::getUserId,user.getId())));
    }
    //添加购物车
    @PostMapping("/addCart")
    public ApiResult<Object> addCart(HttpServletRequest request,@RequestBody ShopCart param) {
        User user = redisService.getCurrentUser(request);
        if(param.getPurchaseQuantity()<=0){
            throw new MyException("数量错误");
        }
        param.setUserId(user.getId());
        return ApiResult.ok(cartService.addCart(param));
    }
    //删除购物车
    @GetMapping("/del")
    public ApiResult<Object> del(HttpServletRequest request, @RequestParam("ids") List<Long> ids) {
        User user = redisService.getCurrentUser(request);

        return ApiResult.ok(cartService.removeByIds(ids));
    }
    //修改规格，增减数量
    @PostMapping("/update")
    public ApiResult<Object> update(HttpServletRequest request,@RequestBody  ShopCart param) {
        User user = redisService.getCurrentUser(request);
        int num= param.getPurchaseQuantity();
        if(num<=0){
            throw new MyException("数量错误");
        }
        ShopCart shopCart=cartService.getById(param.getId());
        if(shopCart==null || !Objects.equals(shopCart.getUserId(), user.getId())) {
            throw new MyException("购物车错误，请刷新后重试");
        }
        BigDecimal amt=param.getAmt();
        ShopCart cartU=new ShopCart();
        cartU.setId(param.getId());
        cartU.setSkuId(param.getSkuId());
        cartU.setPurchaseQuantity(num);
        cartU.setAmt(amt);
        cartU.setTotalAmount(amt.multiply(new BigDecimal(num)));
        return ApiResult.ok(cartService.updateById(cartU));
    }
    //结算
//    CartOrderDTO
    @PostMapping("/settle")
    public ApiResult<Object> settle(HttpServletRequest request, @RequestBody CartDto param) {
        User user = redisService.getCurrentUser(request);
        return ApiResult.ok(cartService.settle(param.getIds(), user.getId(),param.getAddressId()));
    }
    //下单
    @PostMapping("/createOrder")
    public ApiResult<Object> createOrder(HttpServletRequest request,@RequestBody CartDto param ) {
        User user = redisService.getCurrentUser(request);
        return ApiResult.ok(cartService.createOrder(param.getIds(), user.getId(),param.getAddressId(),param.getIsPass()));
    }
}
