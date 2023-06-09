package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.GoodsReview;
import com.shop.tsshop.web.model.dto.GoodsSearchDto;
import com.shop.tsshop.web.model.dto.ReviewDto;
import com.shop.tsshop.web.model.vo.GoodsReviewVo;
import com.shop.tsshop.web.model.vo.UserReviewVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-16
 */
public interface GoodsReviewService extends IService<GoodsReview> {

    /**
     * 订单评价
     * @param dto              dto
     * @return                 统一返回
     */
    List<GoodsReviewVo> getGoodsReviewVo(GoodsSearchDto dto);

    /**
     * 获取用户评价
     * @param dto              dto
     * @return                 统一返回
     */
    List<UserReviewVo> getUserReviewVo(ReviewDto dto);

    /**
     * 获取订单评价
     * @param dto              dto
     * @return                 统一返回
     */
    Object review(GoodsReview dto);
}
