package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.GoodsPropValue;

/**
 * 规格参数Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IGoodsPropValueService 
{
    /**
     * 查询规格参数
     * 
     * @param valueId 规格参数主键
     * @return 规格参数
     */
    public GoodsPropValue selectGoodsPropValueByValueId(Long valueId);

    /**
     * 查询规格参数列表
     * 
     * @param goodsPropValue 规格参数
     * @return 规格参数集合
     */
    public List<GoodsPropValue> selectGoodsPropValueList(GoodsPropValue goodsPropValue);

    /**
     * 新增规格参数
     * 
     * @param goodsPropValue 规格参数
     * @return 结果
     */
    public int insertGoodsPropValue(GoodsPropValue goodsPropValue);

    /**
     * 修改规格参数
     * 
     * @param goodsPropValue 规格参数
     * @return 结果
     */
    public int updateGoodsPropValue(GoodsPropValue goodsPropValue);

    /**
     * 批量删除规格参数
     * 
     * @param valueIds 需要删除的规格参数主键集合
     * @return 结果
     */
    public int deleteGoodsPropValueByValueIds(Long[] valueIds);

    /**
     * 删除规格参数信息
     * 
     * @param valueId 规格参数主键
     * @return 结果
     */
    public int deleteGoodsPropValueByValueId(Long valueId);
}
