package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.GoodsType;

/**
 * 商品类型Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IGoodsTypeService 
{
    /**
     * 查询商品类型
     * 
     * @param id 商品类型主键
     * @return 商品类型
     */
    public GoodsType selectGoodsTypeById(Long id);

    /**
     * 查询商品类型列表
     * 
     * @param goodsType 商品类型
     * @return 商品类型集合
     */
    public List<GoodsType> selectGoodsTypeList(GoodsType goodsType);

    /**
     * 新增商品类型
     * 
     * @param goodsType 商品类型
     * @return 结果
     */
    public int insertGoodsType(GoodsType goodsType);

    /**
     * 修改商品类型
     * 
     * @param goodsType 商品类型
     * @return 结果
     */
    public int updateGoodsType(GoodsType goodsType);

    /**
     * 批量删除商品类型
     * 
     * @param ids 需要删除的商品类型主键集合
     * @return 结果
     */
    public int deleteGoodsTypeByIds(Long[] ids);

    /**
     * 删除商品类型信息
     * 
     * @param id 商品类型主键
     * @return 结果
     */
    public int deleteGoodsTypeById(Long id);
}
