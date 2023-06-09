package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.GoodsPropMapper;
import com.ts.shop.domain.GoodsProp;
import com.ts.shop.service.IGoodsPropService;

/**
 * 规格Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class GoodsPropServiceImpl implements IGoodsPropService 
{
    @Autowired
    private GoodsPropMapper goodsPropMapper;

    /**
     * 查询规格
     * 
     * @param id 规格主键
     * @return 规格
     */
    @Override
    public GoodsProp selectGoodsPropById(Long id)
    {
        return goodsPropMapper.selectGoodsPropById(id);
    }

    /**
     * 查询规格列表
     * 
     * @param goodsProp 规格
     * @return 规格
     */
    @Override
    public List<GoodsProp> selectGoodsPropList(GoodsProp goodsProp)
    {
        return goodsPropMapper.selectGoodsPropList(goodsProp);
    }

    /**
     * 新增规格
     * 
     * @param goodsProp 规格
     * @return 结果
     */
    @Override
    public int insertGoodsProp(GoodsProp goodsProp)
    {
        goodsProp.setCreateTime(DateUtils.getNowDate());
        return goodsPropMapper.insertGoodsProp(goodsProp);
    }

    /**
     * 修改规格
     * 
     * @param goodsProp 规格
     * @return 结果
     */
    @Override
    public int updateGoodsProp(GoodsProp goodsProp)
    {
        return goodsPropMapper.updateGoodsProp(goodsProp);
    }

    /**
     * 批量删除规格
     * 
     * @param ids 需要删除的规格主键
     * @return 结果
     */
    @Override
    public int deleteGoodsPropByIds(Long[] ids)
    {
        return goodsPropMapper.deleteGoodsPropByIds(ids);
    }

    /**
     * 删除规格信息
     * 
     * @param id 规格主键
     * @return 结果
     */
    @Override
    public int deleteGoodsPropById(Long id)
    {
        return goodsPropMapper.deleteGoodsPropById(id);
    }
}
