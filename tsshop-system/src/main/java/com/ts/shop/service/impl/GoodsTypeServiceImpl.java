package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.GoodsTypeMapper;
import com.ts.shop.domain.GoodsType;
import com.ts.shop.service.IGoodsTypeService;

/**
 * 商品类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService 
{
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    /**
     * 查询商品类型
     * 
     * @param id 商品类型主键
     * @return 商品类型
     */
    @Override
    public GoodsType selectGoodsTypeById(Long id)
    {
        return goodsTypeMapper.selectGoodsTypeById(id);
    }

    /**
     * 查询商品类型列表
     * 
     * @param goodsType 商品类型
     * @return 商品类型
     */
    @Override
    public List<GoodsType> selectGoodsTypeList(GoodsType goodsType)
    {
        return goodsTypeMapper.selectGoodsTypeList(goodsType);
    }

    /**
     * 新增商品类型
     * 
     * @param goodsType 商品类型
     * @return 结果
     */
    @Override
    public int insertGoodsType(GoodsType goodsType)
    {
        goodsType.setCreateTime(DateUtils.getNowDate());
        return goodsTypeMapper.insertGoodsType(goodsType);
    }

    /**
     * 修改商品类型
     * 
     * @param goodsType 商品类型
     * @return 结果
     */
    @Override
    public int updateGoodsType(GoodsType goodsType)
    {
        goodsType.setUpdateTime(DateUtils.getNowDate());
        return goodsTypeMapper.updateGoodsType(goodsType);
    }

    /**
     * 批量删除商品类型
     * 
     * @param ids 需要删除的商品类型主键
     * @return 结果
     */
    @Override
    public int deleteGoodsTypeByIds(Long[] ids)
    {
        return goodsTypeMapper.deleteGoodsTypeByIds(ids);
    }

    /**
     * 删除商品类型信息
     * 
     * @param id 商品类型主键
     * @return 结果
     */
    @Override
    public int deleteGoodsTypeById(Long id)
    {
        return goodsTypeMapper.deleteGoodsTypeById(id);
    }
}
