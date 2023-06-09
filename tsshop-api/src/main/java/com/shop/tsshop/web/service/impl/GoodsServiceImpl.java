package com.shop.tsshop.web.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.mapper.LiveStoreGoodsMapper;
import com.shop.tsshop.web.mapper.LiveStoreMapper;
import com.shop.tsshop.web.model.domain.*;
import com.shop.tsshop.web.mapper.GoodsMapper;
import com.shop.tsshop.web.model.dto.GoodsSearchDto;
import com.shop.tsshop.web.model.vo.GoodsDetailVo;
import com.shop.tsshop.web.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    GoodsCollectService goodsCollectService;
    @Autowired
    SeckillService seckillService;
    @Autowired
    SeckillGoodsService seckillGoodsService;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    RedisService redisService;
    @Autowired
    LiveStoreMapper liveStoreMapper;

    @Autowired
    LiveStoreGoodsMapper liveStoreGoodsMapper;

    @Override
    public GoodsDetailVo goodsDetail(Long id) {
        GoodsDetailVo detailVo=new GoodsDetailVo();
        Goods goods=baseMapper.selectById(id);
        BeanUtils.copyProperties(goods,detailVo);

        List<SeckillGoods> list=seckillGoodsService.getGoodsListById(goods.getId());
        detailVo.setSeckillSatus(0);
        if(list.size()!=0){
            Seckill seckill=seckillService.getOne(new LambdaQueryWrapper<Seckill>().eq(Seckill::getStatus,1));

            Date date= new Date(Math.max(new Date().getTime(),seckill.getStartTime().getTime()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.SECOND,0); //这是将当天的【秒】设置为0
            calendar.set(Calendar.MINUTE,0); //这是将当天的【分】设置为0
            calendar.set(Calendar.HOUR_OF_DAY,list.get(0).getTime()); //这是将当天的【时】设置为0
            date=calendar.getTime();
            if((new Date()).getTime()>date.getTime()){
                detailVo.setSeckillSatus(2);
                calendar.set(Calendar.HOUR_OF_DAY,0);
                calendar.add(Calendar.DAY_OF_MONTH,1);
                detailVo.setSeckillTime(calendar.getTime());
            }
            else {
                detailVo.setSeckillSatus(1);
            }
        }
        return detailVo;
    }

    @Override
    public List<Goods> getList(GoodsSearchDto dto) {
        return baseMapper.getList(dto);
    }

    @Override
    public int updateStock(int num,Long id) {
        return baseMapper.updateStock(num,id);
    }

    @Override
    public void updateQuantity(Long goodsId, Integer quantity) {
        baseMapper.updateQuantity(goodsId,quantity);
    }

    @Override
    public ApiResult<Object> getLiveStoreAddGoodsList(GoodsSearchDto dto, HttpServletRequest request) {
        User currentUser = redisService.getCurrentUser(request);
        LiveStore liveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getUserId, currentUser.getId()).eq(LiveStore::getDeleted, 0));
        if (ObjectUtil.isNull(liveStore)){
            return ApiResult.fail("店铺信息有误，请刷新重试");
        }
        List<Long> liveStoreGoodsIds = liveStoreGoodsMapper.selectList(new LambdaQueryWrapper<LiveStoreGoods>()
                .eq(LiveStoreGoods::getLiveStoreId, liveStore.getId())
                .eq(LiveStoreGoods::getDeleted, 0))
                .stream()
                .map(LiveStoreGoods::getGoodsId).collect(Collectors.toList());
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        List<Goods> goodsListVos = goodsMapper.selectList(new LambdaQueryWrapper<Goods>()
                .eq(Goods::getDeleted,0)
                .eq(Goods::getShelves,2)
                .like(dto.getKeyword() != null,Goods::getName,dto.getKeyword())
                .notIn(CollUtil.isNotEmpty(liveStoreGoodsIds),Goods::getId,liveStoreGoodsIds)
                .orderByDesc(Goods::getSort));
        PageInfo<Goods> p = new PageInfo<>(goodsListVos);
        return ApiResult.ok(p);
    }

}
