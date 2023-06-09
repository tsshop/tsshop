package com.ts.shop.service.impl;

import java.util.*;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.GoodsSku;
import com.ts.shop.domain.param.GoodsUpdateDTO;
import com.ts.shop.mapper.GoodsPropMapper;
import com.ts.shop.mapper.GoodsPropValueMapper;
import com.ts.shop.mapper.GoodsSkuMapper;
import net.logstash.logback.encoder.org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.GoodsMapper;
import com.ts.shop.domain.Goods;
import com.ts.shop.service.IGoodsService;

/**
 * 商品Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class GoodsServiceImpl implements IGoodsService 
{
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsSkuMapper skuMapper;

    @Autowired
    private GoodsPropMapper propMapper;

    @Autowired
    private GoodsPropValueMapper propValueMapper;
    /**
     * 查询商品
     * 
     * @param id 商品主键
     * @return 商品
     */
    @Override
    public Goods selectGoodsById(Long id)
    {
        return goodsMapper.selectGoodsById(id);
    }

    /**
     * 查询商品列表
     * 
     * @param goods 商品
     * @return 商品
     */
    @Override
    public List<Goods> selectGoodsList(Goods goods)
    {
        return goodsMapper.selectGoodsList(goods);
    }

    /**
     * 新增商品
     * 
     * @param goods 商品
     * @return 结果
     */
    @Override
    public int insertGoods(Goods goods)
    {
        goods.setCreateTime(DateUtils.getNowDate());
        return goodsMapper.insertGoods(goods);
    }

    /**
     * 修改商品
     * 
     * @param goods 商品
     * @return 结果
     */
    @Override
    public int updateGoods(Goods goods)
    {
        goods.setUpdateTime(DateUtils.getNowDate());
        return goodsMapper.updateGoods(goods);
    }

    /**
     * 批量删除商品
     * 
     * @param ids 需要删除的商品主键
     * @return 结果
     */
    @Override
    public int deleteGoodsByIds(Long[] ids)
    {
        return goodsMapper.deleteGoodsByIds(ids);
    }

    /**
     * 删除商品信息
     * 
     * @param id 商品主键
     * @return 结果
     */
    @Override
    public int deleteGoodsById(Long id)
    {
        return goodsMapper.deleteGoodsById(id);
    }

    @Override
    public int create(GoodsUpdateDTO addUpdateDTO) {

        Goods goods = new Goods();

        BeanUtil.copyProperties(addUpdateDTO, goods);

        if (ObjectUtils.isNotEmpty(addUpdateDTO.getId())) {
            goodsMapper.updateGoods(goods);
        } else {
            goodsMapper.insertGoods(goods);
        }

        List<GoodsSku> skuList = addUpdateDTO.getSkuList();

        if (CollectionUtils.isNotEmpty(skuList)) {
//            List<Long> skuIds = skuList.stream().map(GoodsSku::getSkuId).collect(Collectors.toList());
//            while (skuIds.remove(null)) ;
            skuMapper.deleteBySkuIds(goods.getId());
            for (GoodsSku sku : skuList) {
                sku.setDeleted(0);
                if (ObjectUtils.isEmpty(sku.getGoodsId())) {
                    sku.setGoodsId(goods.getId());
                }
                sku.setUpdateTime(new Date());
                skuMapper.insertGoodsSku(sku);
            }
//            skuMapper.insertGoodsSkus(skuList);
        }
        return 1;
    }

    @Override
    public Long countGoodsNum() {
        return goodsMapper.selectCount(new LambdaQueryWrapper<Goods>().eq(Goods::getDeleted,0));
    }
}
