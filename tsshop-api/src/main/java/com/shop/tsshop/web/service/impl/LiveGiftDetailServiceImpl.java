package com.shop.tsshop.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.mapper.LiveGiftMapper;
import com.shop.tsshop.web.model.domain.LiveGift;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.PageDto;
import com.shop.tsshop.web.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.model.domain.LiveGiftDetail;
import com.shop.tsshop.web.mapper.LiveGiftDetailMapper;
import com.shop.tsshop.web.service.LiveGiftDetailService;
/**
 * @ClassName LiveGiftDetailServiceImpl
 * @Author TS SHOP
 * @create 2023/5/23
 */

@Service
public class LiveGiftDetailServiceImpl extends ServiceImpl<LiveGiftDetailMapper, LiveGiftDetail> implements LiveGiftDetailService{

    @Resource
    private LiveGiftDetailMapper liveGiftDetailMapper;

    @Resource
    private LiveGiftMapper liveGiftMapper;

    @Resource
    private RedisService redisService;

    @Override
    public ApiResult<Object> liveGiftDetailList(PageDto pageDto, HttpServletRequest request) {
        User currentUser = redisService.getCurrentUser(request);
        PageHelper.startPage(pageDto.getPageNumber(),pageDto.getPageSize());
        List<LiveGiftDetail> liveGiftDetails = liveGiftDetailMapper.selectList(new LambdaQueryWrapper<LiveGiftDetail>().eq(LiveGiftDetail::getFromUserId, currentUser.getId()).orderByDesc(LiveGiftDetail::getCreateTime));
        if (CollectionUtils.isNotEmpty(liveGiftDetails)){
            liveGiftDetails.forEach(item -> {
                LiveGift liveGift = liveGiftMapper.selectById(item.getGiftId());
                item.setGiftName(liveGift.getGiftName());
            });
        }
        PageInfo<LiveGiftDetail> pageInfo = new PageInfo<>(liveGiftDetails);
        return ApiResult.ok(pageInfo);
    }
}
