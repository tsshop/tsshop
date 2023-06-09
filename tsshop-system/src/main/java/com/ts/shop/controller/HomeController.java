package com.ts.shop.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ts.common.annotation.Log;
import com.ts.common.core.controller.BaseController;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.enums.BusinessType;
import com.ts.shop.domain.vo.RankingListVo;
import com.ts.shop.service.IGoodsService;
import com.ts.shop.service.IOrderService;
import com.ts.shop.service.IReturnOrderService;
import com.ts.shop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop/home")
public class HomeController  extends BaseController{

    @Autowired
    IUserService userService;
    @Autowired
    IOrderService orderService;
    @Autowired
    IReturnOrderService returnOrderService;

    @Autowired
    IGoodsService goodsService;

    @GetMapping("getHome")
    public AjaxResult getHome()
    {
        JSONObject json=new JSONObject();
        json.put("userNum",userService.count(0));//用户数
        json.put("dailySales",orderService.getDailySales());//今日销售额
        json.put("dailyOrder",orderService.getDailyOrder());//今日订单数
        json.put("dailyNewUser",userService.count(1));//今日新增用户
        json.put("MonthlySalesList",orderService.getMonthlySalesList());//本月成交额（列表）
        json.put("MonthlyOrderList",orderService.getMonthlyOrderList());//本月订单数（列表）

        json.put("MonthlyOrder",10);//销售总量（本月）
        json.put("MonthlyReturn",returnOrderService.getMonthly(3));//退款总量（本月）
        json.put("MonthlySuccess",10);//成功总量（本月）
        return success(json);
    }

    @GetMapping("count")
    public AjaxResult count()
    {
        JSONObject json=new JSONObject();
        json.put("userNum",userService.count(0)); //用户数
        json.put("dailySales",orderService.getDailySales()); //今日销售额
        json.put("dailyOrder",orderService.getDailyOrder()); //今日订单数
        json.put("dailyNewUser",userService.count(1));//今日新增用户
        json.put("goodsNum",goodsService.countGoodsNum());
        return success(json);
    }

    @GetMapping("orderStatus")
    public AjaxResult countOrderStatus(Integer type)
    {
        return orderService.countOrderStatus(type);
    }

    @GetMapping("waitMatter")
    public AjaxResult countWaitMatter()
    {
        return orderService.countWaitMatter();
    }

    @GetMapping("salesRanking")
    public AjaxResult countSalesRanking(Integer type)
    {
        return orderService.countSalesRanking(type);
    }

    @GetMapping("turnover")
    public AjaxResult countTurnover(Integer type)
    {
        return orderService.countTurnover(type);
    }

    @GetMapping("orderNum")
    public AjaxResult countOrderNum(Integer type)
    {
        return orderService.countOrderNum(type);
    }
    @GetMapping("getAmtRanking")
    public AjaxResult getAmtRanking(Integer status) {
        List<RankingListVo> list=orderService.getAmtRanking(status);
        return success(list);
    }
    @GetMapping("getNumRanking")
    public AjaxResult getNumRanking(Integer status) {
        List<RankingListVo> list=orderService.getNumRanking(status);
        return success(list);
    }

}
