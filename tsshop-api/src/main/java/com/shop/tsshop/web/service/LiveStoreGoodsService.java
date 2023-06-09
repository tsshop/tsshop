package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.LiveStoreGoods;
import com.shop.tsshop.web.model.dto.EditLiveStoreGoodsDto;
import com.shop.tsshop.web.model.dto.LiveStoreAddGoodsDto;
import com.shop.tsshop.web.model.dto.LiveStoreDelGoodsDto;
import com.shop.tsshop.web.model.dto.PageDto;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LiveStoreGoodsService
 * @Author TsShop
 * @create 2023/5/23
 */

public interface LiveStoreGoodsService extends IService<LiveStoreGoods>{

    /**
     * 商品列表
     * @param pageDto                   分页参数
     * @return                          统一返回
     */
    ApiResult<Object> liveStoreGoodsList(PageDto pageDto, HttpServletRequest request);


    /**
     * 获取店铺商品信息
     * @param goodsId                   商品 ID
     * @return                          统一返回
     */
    ApiResult<Object> getGoodsInfo(Long goodsId,HttpServletRequest request);

    /**
     * 添加商品
     * @param liveStoreAddGoodsDto     dto
     * @return                         统一返回
     */
    ApiResult<Object> addGoods(LiveStoreAddGoodsDto liveStoreAddGoodsDto,HttpServletRequest request);

    /**
     * 编辑商品
     * @param editLiveStoreGoodsDto    dto
     * @return                         统一返回
     */
    ApiResult<Object> editGoods(EditLiveStoreGoodsDto editLiveStoreGoodsDto,HttpServletRequest request);

    /**
     * 删除商品
     * @param liveStoreAddGoodsDto     dto
     * @param request                  request
     * @return                         统一返回
     */
    ApiResult<Object> liveStoreDelGoods(LiveStoreDelGoodsDto liveStoreAddGoodsDto, HttpServletRequest request);
}
