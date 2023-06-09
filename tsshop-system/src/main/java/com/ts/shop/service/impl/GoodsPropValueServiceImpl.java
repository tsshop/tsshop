package com.ts.shop.service.impl;

import java.util.List;
import com.ts.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.GoodsPropValueMapper;
import com.ts.shop.domain.GoodsPropValue;
import com.ts.shop.service.IGoodsPropValueService;

/**
 * 规格参数Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class GoodsPropValueServiceImpl implements IGoodsPropValueService 
{
    @Autowired
    private GoodsPropValueMapper goodsPropValueMapper;

    /**
     * 查询规格参数
     * 
     * @param valueId 规格参数主键
     * @return 规格参数
     */
    @Override
    public GoodsPropValue selectGoodsPropValueByValueId(Long valueId)
    {
        return goodsPropValueMapper.selectGoodsPropValueByValueId(valueId);
    }

    /**
     * 查询规格参数列表
     * 
     * @param goodsPropValue 规格参数
     * @return 规格参数
     */
    @Override
    public List<GoodsPropValue> selectGoodsPropValueList(GoodsPropValue goodsPropValue)
    {
        return goodsPropValueMapper.selectGoodsPropValueList(goodsPropValue);
    }

    /**
     * 新增规格参数
     * 
     * @param goodsPropValue 规格参数
     * @return 结果
     */
    @Override
    public int insertGoodsPropValue(GoodsPropValue goodsPropValue)
    {
        goodsPropValue.setCreateTime(DateUtils.getNowDate());
        return goodsPropValueMapper.insertGoodsPropValue(goodsPropValue);
    }

    /**
     * 修改规格参数
     * 
     * @param goodsPropValue 规格参数
     * @return 结果
     */
    @Override
    public int updateGoodsPropValue(GoodsPropValue goodsPropValue)
    {
        return goodsPropValueMapper.updateGoodsPropValue(goodsPropValue);
    }

    /**
     * 批量删除规格参数
     * 
     * @param valueIds 需要删除的规格参数主键
     * @return 结果
     */
    @Override
    public int deleteGoodsPropValueByValueIds(Long[] valueIds)
    {
        return goodsPropValueMapper.deleteGoodsPropValueByValueIds(valueIds);
    }

    /**
     * 删除规格参数信息
     * 
     * @param valueId 规格参数主键
     * @return 结果
     */
    @Override
    public int deleteGoodsPropValueByValueId(Long valueId)
    {
        return goodsPropValueMapper.deleteGoodsPropValueByValueId(valueId);
    }
}
