package com.ts.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ts.common.core.domain.AjaxResult;
import com.ts.shop.domain.dto.LiveStoreGoodsListDto;
import com.ts.shop.domain.vo.LiveStoreGoodsVo;
import com.ts.shop.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ts.shop.mapper.LiveStoreGoodsMapper;
import com.ts.shop.domain.LiveStoreGoods;
import com.ts.shop.service.LiveStoreGoodsService;
/**
 * @ClassName LiveStoreGoodsServiceImpl
 * @Author TS SHOP
 * @create 2023/5/29
 */

@Service
public class LiveStoreGoodsServiceImpl extends ServiceImpl<LiveStoreGoodsMapper, LiveStoreGoods> implements LiveStoreGoodsService{

    @Autowired
    LiveStoreGoodsMapper liveStoreGoodsMapper;

    @Autowired
    OrderMapper orderMapper;

    @Override
    public AjaxResult liveStoreGoodsList(LiveStoreGoodsListDto dto) {
        PageHelper.startPage(dto.getPageNumber(),dto.getPageSize());
        List<LiveStoreGoodsVo> liveStoreGoodsVos = liveStoreGoodsMapper.selectGoodsList(dto);
        PageInfo<LiveStoreGoodsVo> pageInfo = new PageInfo<>(liveStoreGoodsVos);
        return AjaxResult.success(pageInfo);
    }
}
