package com.shop.tsshop.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.GoodsCollect;
import com.shop.tsshop.web.model.domain.StoreCollect;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.service.StoreCollectService;
import com.shop.tsshop.web.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 店铺收藏 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
@RestController
@RequestMapping("/storeCollect")
public class StoreCollectController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private StoreCollectService collectService;

    //添加收藏
    @GetMapping("/add")
    public ApiResult<Object> add(Long storeId, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        StoreCollect storeCollect=new StoreCollect();
        storeCollect.setStoreId(storeId);
        storeCollect.setUserId(user.getId());
        return ApiResult.ok(collectService.save(storeCollect));
    }
    //删除收藏
    @GetMapping("/delete")
    public ApiResult<Object> delete(Long storeId, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        return ApiResult.ok(collectService.remove(new LambdaQueryWrapper<StoreCollect>().eq(StoreCollect::getStoreId,storeId).eq(StoreCollect::getUserId,user.getId())));
    }
    //收藏列表
    @GetMapping("/getList")
    public ApiResult<Object> getList(HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        return ApiResult.ok();
    }
}
