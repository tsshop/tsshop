package com.shop.tsshop.web.mapper;

import com.shop.tsshop.web.model.domain.GoodsReview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.dto.GoodsSearchDto;
import com.shop.tsshop.web.model.dto.ReviewDto;
import com.shop.tsshop.web.model.vo.GoodsReviewVo;
import com.shop.tsshop.web.model.vo.UserReviewVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-02-16
 */
public interface GoodsReviewMapper extends BaseMapper<GoodsReview> {

    List<GoodsReviewVo> getGoodsReviewVo(GoodsSearchDto dto);

    List<UserReviewVo> getUserReviewVo(ReviewDto dto);
}
