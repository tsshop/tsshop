package com.shop.tsshop.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.model.domain.Goods;
import com.shop.tsshop.web.model.domain.GoodsCollect;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.service.GoodsCollectService;
import com.shop.tsshop.web.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品收藏 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
@RestController
@RequestMapping("/goodsCollect")
public class GoodsCollectController {

    @Autowired
    GoodsCollectService collectService;
    @Autowired
    private RedisService redisService;
    //添加收藏
    @GetMapping("/add")
    public ApiResult<Object> add(Long goodsId, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        GoodsCollect collect=new GoodsCollect();
        collect.setGoodsId(goodsId);
        collect.setUserId(user.getId());
        try {
            collectService.save(collect);
        }catch (Exception e){
        }
        return ApiResult.ok();
    }
    //删除收藏
    @GetMapping("/delete")
    public ApiResult<Object> delete(@RequestParam("goodsIds")  List<Long> goodsIds, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        return ApiResult.ok(collectService.remove(new LambdaQueryWrapper<GoodsCollect>().in(GoodsCollect::getGoodsId,goodsIds).eq(GoodsCollect::getUserId,user.getId())));
    }
    //收藏列表
    @GetMapping("/getList")
    public ApiResult<Object> getList(HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        List<Goods> list=collectService.getCollect(user.getId());
        return ApiResult.ok(list);
    }
    //收藏列表
    @GetMapping("/getIsCollect")
    @PassToken
    public ApiResult<Object> getIsCollect(HttpServletRequest request,Long goodsId) {
        int k=0;
        try {
            User user = redisService.getCurrentUser(request);
            k=collectService.count(new LambdaQueryWrapper<GoodsCollect>().eq(GoodsCollect::getGoodsId,goodsId).eq(GoodsCollect::getUserId,user.getId()));
        }catch (Exception e){

        }
        return ApiResult.ok(k);
    }
//
}
