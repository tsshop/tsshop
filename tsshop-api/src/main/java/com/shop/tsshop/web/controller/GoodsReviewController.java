package com.shop.tsshop.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.model.domain.Goods;
import com.shop.tsshop.web.model.domain.GoodsReview;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.GoodsSearchDto;
import com.shop.tsshop.web.model.dto.ReviewDto;
import com.shop.tsshop.web.model.vo.GoodsReviewVo;
import com.shop.tsshop.web.model.vo.UserReviewVo;
import com.shop.tsshop.web.service.GoodsReviewService;
import com.shop.tsshop.web.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-16
 */
@RestController
@RequestMapping("/goodsReview")
public class GoodsReviewController {

    @Autowired
    private GoodsReviewService goodsReviewService;
    @Autowired
    private RedisService redisService;
    //商品评论列表
    @PostMapping("/getGoodsReviewList")
    public ApiResult<Object> getGoodsReviewList(@RequestBody GoodsSearchDto dto) {
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        List<GoodsReviewVo> listVos=goodsReviewService.getGoodsReviewVo(dto);
        PageInfo<GoodsReviewVo> p = new PageInfo<>(listVos);
        return ApiResult.ok(p);
    }
    //用户评论列表
    @PostMapping("/getUserReviewList")
    public ApiResult<Object> getUserReviewList(HttpServletRequest request,@RequestBody ReviewDto dto) {
        User user = redisService.getCurrentUser(request);
        dto.setUserId(user.getId());
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        List<UserReviewVo> listVos=goodsReviewService.getUserReviewVo(dto);
        PageInfo<UserReviewVo> p = new PageInfo<>(listVos);
        return ApiResult.ok(p);
    }
    //评论
    @PostMapping("/review")
    public ApiResult<Object> review(HttpServletRequest request,@RequestBody GoodsReview dto) {
        User user = redisService.getCurrentUser(request);
        dto.setUserId(user.getId());
        return ApiResult.ok(goodsReviewService.review(dto));
    }

}
