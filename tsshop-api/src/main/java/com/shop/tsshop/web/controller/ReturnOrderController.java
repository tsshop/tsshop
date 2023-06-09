package com.shop.tsshop.web.controller;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.*;
import com.shop.tsshop.web.model.dto.ReturnApplyDto;
import com.shop.tsshop.web.model.dto.ReturnExpressDto;
import com.shop.tsshop.web.model.vo.OrderDetailVo;
import com.shop.tsshop.web.model.vo.ReturnOrderListVo;
import com.shop.tsshop.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p>
 * 退款表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-10
 */
@RestController
@RequestMapping("/returnOrder")
public class ReturnOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReturnOrderService returnOrderService;
    @Autowired
    private ReturnAddressService returnAddressService;

    @Autowired
    AreaService areaService;
    //退款订单详情
    @GetMapping("/detail")
    public ApiResult<Object> detail(Long returnId, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        ReturnOrder returnOrder=returnOrderService.getById(returnId);
        if (null == returnOrder || !Objects.equals(user.getId(), returnOrder.getUserId())) {
            return ApiResult.fail("订单不存在！");
        }
        Order order = orderService.getOne(new LambdaQueryWrapper<Order>().eq(Order::getOrderNo,returnOrder.getOrderNo()));
        if (null == order || !Objects.equals(user.getId(), order.getUserId())) {
            return ApiResult.fail("订单不存在！");
        }
        JSONObject json=new JSONObject();
        json.put("returnOrder",returnOrder);
        OrderDetailVo orderDetailVo = orderService.getDetail(order);
        json.put("order",orderDetailVo);

        if(returnOrder.getStatus()==4){
            ReturnAddress address = returnAddressService.getById(returnOrder.getAddressId());
            Area pr = areaService.getById(address.getProvince());
            Area ci = areaService.getById(address.getCity());
            Area ar = areaService.getById(address.getArea());
            address.setAreaName(ar.getAreaName());
            address.setProvinceName(pr.getAreaName());
            address.setCityName(ci.getAreaName());
            json.put("address",address);
        }
        return ApiResult.ok(json);
    }
    //查看列表
    @GetMapping("/getList")
    public ApiResult<Object> getList(int pageNumber, int pageSize, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        PageHelper.startPage(pageNumber, pageSize ,"create_time desc");
        PageInfo<ReturnOrderListVo> p = new PageInfo<>(returnOrderService.getList(user.getId()));
        return ApiResult.ok(p);
    }
    //发起退款
    @PostMapping("/apply")
    public ApiResult<Object> apply(@RequestBody ReturnApplyDto applyParam, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        ReturnOrder returnOrder=new ReturnOrder();
        Order order=orderService.getOne(new LambdaQueryWrapper<Order>().eq(Order::getOrderNo,applyParam.getOrderNo()));
        if (null == order || !Objects.equals(user.getId(), order.getUserId())) {
            return ApiResult.fail("订单不存在！");
        }
        Integer orderStatus=order.getOrderStatus();
        if (orderStatus!=1 && orderStatus!=2 && orderStatus!=3) {
            return ApiResult.fail("此订单不可退款！");
        }
        Long returnId = order.getReturnId();
        if(returnId !=null && returnId !=0){
            ReturnOrder r=returnOrderService.getById(returnId);
            if(r!=null && r.getStatus()!=2){
                return ApiResult.fail("还有未处理的订单！");
            }
        }
        BigDecimal total = order.getTotalAmount();
        BigDecimal returnAmt = applyParam.getReturnAmt();
        if(total.compareTo(returnAmt) < 0  || returnAmt.doubleValue()<=0){
            return ApiResult.fail("金额超出范围！");
        }
        BeanUtil.copyProperties(applyParam, returnOrder);
        returnOrder.setStatus(1);
        returnOrder.setOrderAmt(total);
        returnOrder.setUserId(user.getId());
        returnOrderService.save(returnOrder);
        return ApiResult.ok(returnOrder.getId());
    }
    //填写单号
    @PostMapping("/express")
    public ApiResult<Object> express(@RequestBody ReturnExpressDto applyVo, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        ReturnOrder returnOrder=returnOrderService.getById(applyVo.getId());
        if (null == returnOrder || !Objects.equals(user.getId(), returnOrder.getUserId())) {
            return ApiResult.fail("订单不存在！");
        }
        if(returnOrder.getStatus()!=4){
            return ApiResult.fail("订单状态不正确，请刷新后重试！");
        }
        ReturnOrder rUpdate=new ReturnOrder();
        rUpdate.setId(applyVo.getId());
        rUpdate.setStatus(5);
        rUpdate.setExpressName(applyVo.getExpressName());
        rUpdate.setExpress(applyVo.getExpress());
        rUpdate.setExpressNo(applyVo.getExpressNo());
        returnOrderService.updateById(rUpdate);
        return ApiResult.ok();
    }
}
