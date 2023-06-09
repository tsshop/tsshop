package com.shop.tsshop.web.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.enums.NumberEnmus;
import com.shop.tsshop.web.mapper.*;
import com.shop.tsshop.web.model.domain.*;
import com.shop.tsshop.web.model.dto.EditLiveStoreGoodsDto;
import com.shop.tsshop.web.model.dto.LiveStoreAddGoodsDto;
import com.shop.tsshop.web.model.dto.LiveStoreDelGoodsDto;
import com.shop.tsshop.web.model.dto.PageDto;
import com.shop.tsshop.web.model.vo.LiveStoreDetialVo;
import com.shop.tsshop.web.model.vo.LiveStoreGoodsInfoVo;
import com.shop.tsshop.web.model.vo.LiveStoreGoodsVo;
import com.shop.tsshop.web.service.RedisService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.service.LiveStoreGoodsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName LiveStoreGoodsServiceImpl
 * @Author TS SHOP
 * @create 2023/5/23
 */

@Service
public class LiveStoreGoodsServiceImpl extends ServiceImpl<LiveStoreGoodsMapper, LiveStoreGoods> implements LiveStoreGoodsService{

    @Autowired
    private RedisService redisService;

    @Autowired
    LiveStoreMapper liveStoreMapper;

    @Autowired
    LiveStoreGoodsMapper liveStoreGoodsMapper;

    @Autowired
    GoodsSkuMapper goodsSkuMapper;

    @Autowired
    LiveStoreGoodsSkuMapper liveStoreGoodsSkuMapper;

    @Autowired
    GoodsMapper goodsMapper;


    @Override
    public ApiResult<Object> liveStoreGoodsList(PageDto pageDto, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        LiveStore liveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getUserId, user.getId()).eq(LiveStore::getDeleted, NumberEnmus.ZERO.getNumber()));
        if (ObjectUtil.isNull(liveStore)){
            return ApiResult.fail("店铺信息有误，请刷新重试");
        }
        PageHelper.startPage(pageDto.getPageNumber(),pageDto.getPageSize());
        List<LiveStoreGoodsVo> liveStoreGoodsVos = liveStoreGoodsMapper.selectGoodsList(pageDto, liveStore.getId());
        liveStoreGoodsVos.forEach(item ->{
            List<LiveStoreGoodsSku> liveStoreGoodsSkus = liveStoreGoodsSkuMapper.selectList(new LambdaQueryWrapper<LiveStoreGoodsSku>().eq(LiveStoreGoodsSku::getLiveStoreGoodsId, item.getId()).eq(LiveStoreGoodsSku::getLiveStoreId, liveStore.getId()).eq(LiveStoreGoodsSku::getDeleted, false));
            liveStoreGoodsSkus.forEach(liveStoreGoodsSku -> {
                GoodsSku goodsSku = goodsSkuMapper.selectById(liveStoreGoodsSku.getSkuId());
                liveStoreGoodsSku.setSkuName(goodsSku.getSkuName());
                liveStoreGoodsSku.setStocks(goodsSku.getStocks());
            });
            item.setLiveStoreGoodsSkus(liveStoreGoodsSkus);
        });
        PageInfo<LiveStoreGoodsVo> pageInfo = new PageInfo<>(liveStoreGoodsVos);
        return ApiResult.ok(pageInfo);
    }

    @Override
    public ApiResult<Object> getGoodsInfo(Long goodsId,HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        LiveStore liveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getUserId, user.getId()).eq(LiveStore::getDeleted, NumberEnmus.ZERO.getNumber()));
        LiveStoreGoodsInfoVo liveStoreGoodsInfoVo = new LiveStoreGoodsInfoVo();
        Goods goods = goodsMapper.selectOne(new LambdaQueryWrapper<Goods>().eq(Goods::getId,goodsId).eq(Goods::getDeleted,NumberEnmus.ZERO.getNumber()));
        LiveStoreGoods liveStoreGoods = liveStoreGoodsMapper.selectOne(new LambdaQueryWrapper<LiveStoreGoods>()
                .eq(LiveStoreGoods::getGoodsId, goodsId)
                .eq(LiveStoreGoods::getLiveStoreId,liveStore.getId())
                .eq(LiveStoreGoods::getDeleted, NumberEnmus.ZERO.getNumber()));
        if (ObjectUtil.isNull(goods)){
            return ApiResult.fail("商品信息有误，请刷新重试");
        }
        List<GoodsSku> goodsSkus = goodsSkuMapper.selectList(new LambdaQueryWrapper<GoodsSku>().eq(GoodsSku::getGoodsId, goodsId).eq(GoodsSku::getDeleted, NumberEnmus.ZERO.getNumber()));
        List<LiveStoreGoodsSku> liveStoreGoodsSkus = liveStoreGoodsSkuMapper.selectList(new LambdaQueryWrapper<LiveStoreGoodsSku>()
                .in(LiveStoreGoodsSku::getSkuId, goodsSkus.stream().map(GoodsSku::getSkuId).collect(Collectors.toList()))
                .eq(LiveStoreGoodsSku::getDeleted, NumberEnmus.ZERO.getNumber()));
        liveStoreGoodsSkus.forEach(item ->{
            GoodsSku goodsSku = goodsSkuMapper.selectById(item.getSkuId());
            item.setSkuName(goodsSku.getSkuName());
        });
        liveStoreGoodsInfoVo.setId(goodsId);
        liveStoreGoodsInfoVo.setHeadPortrait(goods.getHeadPortrait());
        liveStoreGoodsInfoVo.setName(goods.getName());
        liveStoreGoodsInfoVo.setOrderBy(liveStoreGoods.getOrderBy());
        liveStoreGoodsInfoVo.setLiveStoreGoodsSkus(liveStoreGoodsSkus);
        return ApiResult.ok(liveStoreGoodsInfoVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiResult<Object> addGoods(LiveStoreAddGoodsDto dto,HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        LiveStore liveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getUserId, user.getId()).eq(LiveStore::getDeleted, NumberEnmus.ZERO.getNumber()));
        // 校验商品信息
        List<Goods> goods = goodsMapper.selectBatchIds(dto.getGoodsIds());
        for (Goods good : goods) {
            if (Objects.equals(good.getDeleted(), NumberEnmus.ONE.getNumber()) || Objects.equals(good.getShelves(), NumberEnmus.ONE.getNumber())){
                return ApiResult.fail("商品信息有误，请刷新重试");
            }
        }
        List<GoodsSku> goodsSkus = goodsSkuMapper.selectList(new LambdaQueryWrapper<GoodsSku>().in(GoodsSku::getGoodsId, dto.getGoodsIds()).eq(GoodsSku::getDeleted,NumberEnmus.ZERO.getNumber()));
        Map<Long, List<GoodsSku>> collect = goodsSkus.stream().collect(Collectors.groupingBy(GoodsSku::getGoodsId));
        dto.getGoodsIds().forEach(item -> {
            LiveStoreGoods liveStoreGoods = liveStoreGoodsMapper.selectOne(new LambdaQueryWrapper<LiveStoreGoods>().eq(LiveStoreGoods::getLiveStoreId, liveStore.getId()).eq(LiveStoreGoods::getGoodsId, item).eq(LiveStoreGoods::getDeleted, NumberEnmus.ZERO.getNumber()));
            if (ObjectUtil.isNull(liveStoreGoods)){
                LiveStoreGoods storeGoods = new LiveStoreGoods();
                storeGoods.setLiveStoreId(liveStore.getId());
                storeGoods.setGoodsId(item);
                storeGoods.setDeleted(NumberEnmus.ZERO.getNumber());
                liveStoreGoodsMapper.insert(storeGoods);
                addSku(storeGoods.getGoodsId(),collect.get(storeGoods.getGoodsId()),liveStore.getId());
            }
        });
        return ApiResult.ok();
    }

    @Override
    public ApiResult<Object> editGoods(EditLiveStoreGoodsDto dto,HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        LiveStore liveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getUserId, user.getId()).eq(LiveStore::getDeleted, NumberEnmus.ZERO.getNumber()));
        LiveStoreGoods liveStoreGoods = liveStoreGoodsMapper.selectOne(new LambdaQueryWrapper<LiveStoreGoods>()
                .eq(LiveStoreGoods::getGoodsId,dto.getId())
                .eq(LiveStoreGoods::getLiveStoreId,liveStore.getId())
                .eq(LiveStoreGoods::getDeleted,NumberEnmus.ZERO.getNumber()));
        if (dto.getOrderBy() != null){
            if (dto.getOrderBy() < NumberEnmus.ZERO.getNumber()){
                return ApiResult.fail("排序不能小于0");
            }
        }
        liveStoreGoods.setOrderBy(dto.getOrderBy());
        liveStoreGoodsMapper.updateById(liveStoreGoods);
        for (LiveStoreGoodsSku liveStoreGoodsSkus : dto.getLiveStoreGoodsSkus()) {
            LiveStoreGoodsSku liveStoreGoodsSku = liveStoreGoodsSkuMapper.selectById(liveStoreGoodsSkus.getId());
            GoodsSku goodsSku = goodsSkuMapper.selectById(liveStoreGoodsSkus.getSkuId());
            if (Objects.equals(liveStoreGoodsSku.getEditFlag(), NumberEnmus.ONE.getNumber()) && Objects.equals(liveStoreGoodsSkus.getEditFlag(), NumberEnmus.ZERO.getNumber())){
                liveStoreGoodsSku.setOriPrice(goodsSku.getOriPrice());
                liveStoreGoodsSku.setPrice(goodsSku.getPrice());
                liveStoreGoodsSku.setEditFlag(NumberEnmus.ZERO.getNumber());
                liveStoreGoodsSkuMapper.updateById(liveStoreGoodsSku);
                continue;
            }
            if (goodsSku.getOriPrice().compareTo(liveStoreGoodsSkus.getOriPrice()) != NumberEnmus.ZERO.getNumber() ||
                    goodsSku.getPrice().compareTo(liveStoreGoodsSkus.getPrice()) != NumberEnmus.ZERO.getNumber()){
                liveStoreGoodsSku.setOriPrice(liveStoreGoodsSkus.getOriPrice());
                liveStoreGoodsSku.setPrice(liveStoreGoodsSkus.getPrice());
                liveStoreGoodsSku.setEditFlag(NumberEnmus.ONE.getNumber());
                liveStoreGoodsSkuMapper.updateById(liveStoreGoodsSku);
            }
        }
        return ApiResult.ok();
    }

    @Override
    public ApiResult<Object> liveStoreDelGoods(LiveStoreDelGoodsDto dto, HttpServletRequest request) {
        User user = redisService.getCurrentUser(request);
        LiveStore liveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getUserId, user.getId()).eq(LiveStore::getDeleted, NumberEnmus.ZERO.getNumber()));
        dto.getGoodsIds().forEach(item ->{
            LiveStoreGoods liveStoreGoods = liveStoreGoodsMapper.selectOne(new LambdaQueryWrapper<LiveStoreGoods>()
                    .eq(LiveStoreGoods::getGoodsId, item)
                    .eq(LiveStoreGoods::getLiveStoreId,liveStore.getId())
                    .eq(LiveStoreGoods::getDeleted, NumberEnmus.ZERO.getNumber()));
            liveStoreGoods.setDeleted(NumberEnmus.ONE.getNumber());
            liveStoreGoodsMapper.updateById(liveStoreGoods);
        });
        List<LiveStoreGoodsSku> liveStoreGoodsSkus = liveStoreGoodsSkuMapper.selectList(new LambdaQueryWrapper<LiveStoreGoodsSku>()
                .in(LiveStoreGoodsSku::getLiveStoreGoodsId, dto.getGoodsIds())
                .eq(LiveStoreGoodsSku::getLiveStoreId, liveStore.getId())
                .eq(LiveStoreGoodsSku::getDeleted, NumberEnmus.ZERO.getNumber()));
        liveStoreGoodsSkus.forEach(item ->{
            item.setDeleted(true);
            liveStoreGoodsSkuMapper.updateById(item);
        });
        return ApiResult.ok();
    }

    /**
     * 添加SKU
     * @param goodsId         SPU
     * @param goodsSkus       SKUS
     */
    private void addSku(Long goodsId, List<GoodsSku> goodsSkus,Long liveStoreId) {
        goodsSkus.forEach(item ->{
            LiveStoreGoodsSku liveStoreGoodsSku = new LiveStoreGoodsSku();
            liveStoreGoodsSku.setSkuId(item.getSkuId());
            liveStoreGoodsSku.setLiveStoreGoodsId(goodsId);
            liveStoreGoodsSku.setLiveStoreId(liveStoreId);
            liveStoreGoodsSku.setPrice(item.getPrice());
            liveStoreGoodsSku.setOriPrice(item.getOriPrice());
            liveStoreGoodsSku.setDeleted(false);
            liveStoreGoodsSkuMapper.insert(liveStoreGoodsSku);
        });
    }
}
