package com.shop.tsshop.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.enums.NumberEnmus;
import com.shop.tsshop.web.mapper.OrderMapper;
import com.shop.tsshop.web.mapper.UserLiveGiftMapper;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.domain.UserLiveGift;
import com.shop.tsshop.web.model.dto.PageDto;
import com.shop.tsshop.web.model.vo.LiveGiftBuyRecordVo;
import com.shop.tsshop.web.model.vo.LiveStoreGoodsVo;
import com.shop.tsshop.web.service.RedisService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.mapper.LiveGiftMapper;
import com.shop.tsshop.web.model.domain.LiveGift;
import com.shop.tsshop.web.service.LiveGiftService;
/**
 * @ClassName LiveGiftServiceImpl
 * @Author TS SHOP
 * @create 2023/5/23
 */

@Service
public class LiveGiftServiceImpl extends ServiceImpl<LiveGiftMapper, LiveGift> implements LiveGiftService{

    @Autowired
    LiveGiftMapper liveGiftMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserLiveGiftMapper userLiveGiftMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ApiResult<Object> giftList(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPageNumber(),pageDto.getPageSize());
        List<LiveGift> liveGifts = liveGiftMapper.selectList(new LambdaQueryWrapper<LiveGift>().eq(LiveGift::getDeleted, false).orderByAsc(LiveGift::getGiftOrderBy));
        PageInfo<LiveGift> pageInfo = new PageInfo<>(liveGifts);
        return ApiResult.ok(pageInfo);
    }

    @Override
    public ApiResult<Object> userGiftInfo(PageDto pageDto, HttpServletRequest request) {
        User currentUser = redisService.getCurrentUser(request);
        PageHelper.startPage(pageDto.getPageNumber(),pageDto.getPageSize());
        List<LiveGift> liveGifts = liveGiftMapper.selectList(new LambdaQueryWrapper<LiveGift>().eq(LiveGift::getDeleted, NumberEnmus.ZERO.getNumber()).orderByAsc(LiveGift::getGiftOrderBy));
        if (CollUtil.isEmpty(liveGifts)){
            return ApiResult.ok();
        }
        PageInfo<LiveGift> pageInfo = new PageInfo<>(liveGifts);
        Map<Long, UserLiveGift> userGiftMap = userLiveGiftMapper.selectList(
                        new LambdaQueryWrapper<UserLiveGift>()
                                .in(UserLiveGift::getLiveGiftId, liveGifts.stream().map(LiveGift::getId).collect(Collectors.toList()))
                                .eq(UserLiveGift::getUserId, currentUser.getId())
                ).stream()
                .collect(Collectors.toMap(UserLiveGift::getLiveGiftId, entity -> entity));
        liveGifts.removeIf(liveGift -> {
            UserLiveGift userLiveGift = userGiftMap.get(liveGift.getId());
            if (ObjectUtil.isNull(userLiveGift) && !liveGift.getShelves()){
                liveGift.setGiftNum(0L);
                return false;
            }
            if (ObjectUtil.isNull(userLiveGift) && liveGift.getShelves()){
                return true;
            }
            if (liveGift.getShelves() && userLiveGift.getLiveGiftNum() == 0) {
                return true;
            } else {
                liveGift.setGiftNum(userLiveGift.getLiveGiftNum());
                return false;
            }
        });
        return ApiResult.ok(pageInfo);
    }

    @Override
    public ApiResult<Object> liveGiftBuyRecord(PageDto pageDto, HttpServletRequest request) {
        User currentUser = redisService.getCurrentUser(request);
        PageHelper.startPage(pageDto.getPageNumber(),pageDto.getPageSize());
        List<LiveGiftBuyRecordVo> liveGiftBuyRecordVos = orderMapper.liveGiftBuyRecord(currentUser.getId());
        PageInfo<LiveGiftBuyRecordVo> pageInfo = new PageInfo<>(liveGiftBuyRecordVos);
        return ApiResult.ok(pageInfo);
    }
}
