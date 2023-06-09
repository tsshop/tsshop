package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.GoodsProp;

/**
 * 规格Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IGoodsPropService 
{
    /**
     * 查询规格
     * 
     * @param id 规格主键
     * @return 规格
     */
    public GoodsProp selectGoodsPropById(Long id);

    /**
     * 查询规格列表
     * 
     * @param goodsProp 规格
     * @return 规格集合
     */
    public List<GoodsProp> selectGoodsPropList(GoodsProp goodsProp);

    /**
     * 新增规格
     * 
     * @param goodsProp 规格
     * @return 结果
     */
    public int insertGoodsProp(GoodsProp goodsProp);

    /**
     * 修改规格
     * 
     * @param goodsProp 规格
     * @return 结果
     */
    public int updateGoodsProp(GoodsProp goodsProp);

    /**
     * 批量删除规格
     * 
     * @param ids 需要删除的规格主键集合
     * @return 结果
     */
    public int deleteGoodsPropByIds(Long[] ids);

    /**
     * 删除规格信息
     * 
     * @param id 规格主键
     * @return 结果
     */
    public int deleteGoodsPropById(Long id);
}
