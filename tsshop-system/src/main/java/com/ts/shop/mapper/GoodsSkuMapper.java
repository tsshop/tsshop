package com.ts.shop.mapper;

import java.util.List;
import com.ts.shop.domain.GoodsSku;
import org.apache.ibatis.annotations.Param;

/**
 * 单品SKUMapper接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface GoodsSkuMapper 
{
    /**
     * 查询单品SKU
     * 
     * @param skuId 单品SKU主键
     * @return 单品SKU
     */
    public GoodsSku selectGoodsSkuBySkuId(Long skuId);

    /**
     * 查询单品SKU列表
     * 
     * @param goodsSku 单品SKU
     * @return 单品SKU集合
     */
    public List<GoodsSku> selectGoodsSkuList(GoodsSku goodsSku);

    /**
     * 新增单品SKU
     * 
     * @param goodsSku 单品SKU
     * @return 结果
     */
    public int insertGoodsSku(GoodsSku goodsSku);

    /**
     * 修改单品SKU
     * 
     * @param goodsSku 单品SKU
     * @return 结果
     */
    public int updateGoodsSku(GoodsSku goodsSku);

    /**
     * 删除单品SKU
     * 
     * @param skuId 单品SKU主键
     * @return 结果
     */
    public int deleteGoodsSkuBySkuId(Long skuId);

    /**
     * 批量删除单品SKU
     * 
     * @param skuIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsSkuBySkuIds(Long[] skuIds);

    void deleteBySkuIds(@Param("goodsId") Long goodsId);
//    void deleteBySkuIds(@Param("goodsId") Long goodsId, @Param("skuIds")  List<Long> skuIds);
}
