package com.shop.tsshop.web.service.impl;

import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.web.model.domain.GoodsReview;
import com.shop.tsshop.web.mapper.GoodsReviewMapper;
import com.shop.tsshop.web.model.domain.Order;
import com.shop.tsshop.web.model.dto.GoodsSearchDto;
import com.shop.tsshop.web.model.dto.ReviewDto;
import com.shop.tsshop.web.model.vo.GoodsReviewVo;
import com.shop.tsshop.web.model.vo.UserReviewVo;
import com.shop.tsshop.web.service.GoodsReviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-16
 */
@Service
public class GoodsReviewServiceImpl extends ServiceImpl<GoodsReviewMapper, GoodsReview> implements GoodsReviewService {

    @Autowired
    OrderService orderService;
    @Override
    public List<GoodsReviewVo> getGoodsReviewVo(GoodsSearchDto dto) {
        List<GoodsReviewVo> list=baseMapper.getGoodsReviewVo(dto);
        for(GoodsReviewVo v:list){
            if(v.getAnonymous()==1){
                v.setUserName("匿名用户");
                v.setAvatar("");
            }
        }
        return list;
    }

    @Override
    public List<UserReviewVo> getUserReviewVo(ReviewDto dto) {
        return baseMapper.getUserReviewVo(dto);
    }

    @Override
    public Object review(GoodsReview dto) {
        Order order=orderService.getById(dto.getOrderId());
        if(order==null || !Objects.equals(order.getUserId(), dto.getUserId()) || order.getOrderStatus()!=3){
            throw new MyException(500,"订单错误");
        }
        Integer score = dto.getScore();
        if(score ==null || score>5 || score<0){
            throw new MyException(500,"参数错误");
        }
        dto.setGoodsId(order.getGoodsId());
        try {
            save(dto);
        }catch (Exception e){
            throw new MyException(500,"已经发表过评价了");
        }
        return 0;
    }
}
