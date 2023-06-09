package com.shop.tsshop.web.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.model.domain.Goods;
import com.shop.tsshop.web.model.domain.GoodsSku;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.GoodsSearchDto;
import com.shop.tsshop.web.model.vo.GoodsDetailVo;
import com.shop.tsshop.web.service.GoodsService;
import com.shop.tsshop.web.service.GoodsSkuService;
import com.shop.tsshop.web.service.GoodsTypeService;
import com.shop.tsshop.web.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsTypeService goodsTypeService;
    @Autowired
    private GoodsSkuService skuService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/getList")
    //"商品列表")
    public ApiResult<Object> getHomeList( Pageable page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<Goods> goodsListVos=goodsService
                .list(new QueryWrapper<Goods>()
                        .eq("shelves",2)
                        .eq("deleted",0)
                        .orderByAsc("sort IS NULL" , "sort"));

        PageInfo<Goods> p = new PageInfo<>(goodsListVos);
        return ApiResult.ok(p);
    }
    @PostMapping("/liveStore/getList")
    @PassToken
    public ApiResult<Object> liveStoreGetGoodsList( @RequestBody GoodsSearchDto dto,HttpServletRequest request) {
        return goodsService.getLiveStoreAddGoodsList(dto,request);
    }
    //分类，关键词，销量，价格，价格区间
    @PostMapping("/getList")
    @PassToken
    //"商品列表")
    public ApiResult<Object> getList(@RequestBody GoodsSearchDto dto) {
        String sort="sort IS NULL , sort";
        Integer typeId = dto.getSortType();
        if(typeId!=null){
            if(typeId==0){
                sort="purchase_quantity";
            }
            else if(typeId==1){
                sort="price";
            }
            if(dto.getSort()!=null && dto.getSort()==1 ){
                sort+=" desc";
            }
        }
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize(),sort);
        List<Goods> goodsListVos=goodsService.getList(dto);
        PageInfo<Goods> p = new PageInfo<>(goodsListVos);
        return ApiResult.ok(p);
    }


    @GetMapping("/detail")
    @PassToken
    //"商品详情")
    public ApiResult<GoodsDetailVo> goodsDetail(Long id) {
        return ApiResult.ok(goodsService.goodsDetail(id));
    }
    @GetMapping("/getSku")
    @PassToken
    //"商品详情")
    public ApiResult<Object> getSku(Long id) {
        return ApiResult.ok(skuService.list(new LambdaQueryWrapper<GoodsSku>().eq(GoodsSku::getGoodsId,id).eq(GoodsSku::getDeleted,0)));
    }
    @GetMapping("/getType")
    @PassToken
    public ApiResult<Object> getType(){
        return ApiResult.ok(goodsTypeService.getType());
    }
}
