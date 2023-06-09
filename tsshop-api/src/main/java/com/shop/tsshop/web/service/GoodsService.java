package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.Goods;
import com.shop.tsshop.web.model.dto.GoodsSearchDto;
import com.shop.tsshop.web.model.vo.GoodsDetailVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
public interface GoodsService extends IService<Goods> {

    /**
     * 获取商品详情
     * @param id                商品 ID
     * @return                  统一返回
     */
    GoodsDetailVo goodsDetail(Long id);

    /**
     * 获取商品列表
     * @param dto               dto
     * @return                  统一返回
     */
    List<Goods> getList(GoodsSearchDto dto);

    /**
     * 更新库存
     * @param num               num
     * @param id                id
     * @return                  统一返回
     */
    int updateStock(int num,Long id);

    /**
     * 修改销量
     * @param goodsId           商品 id
     * @param quantity          销量
     */
    void updateQuantity(Long goodsId, Integer quantity);

    /**
     * 获取店铺添加商品的列表
     * @param page              page
     * @param request           request
     * @return                  统一返回
     */
    ApiResult<Object> getLiveStoreAddGoodsList(GoodsSearchDto dto, HttpServletRequest request);
}
