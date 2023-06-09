package com.ts.shop.service.impl;

import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.PayType;
import com.ts.shop.mapper.PayTypeMapper;
import com.ts.shop.service.IPayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付方式Service业务层处理
 *
 * @author xu
 * @date 2023-04-25
 */
@Service
public class PayTypeServiceImpl implements IPayTypeService
{
    @Autowired
    private PayTypeMapper payTypeMapper;

    /**
     * 查询支付方式
     *
     * @param id 支付方式主键
     * @return 支付方式
     */
    @Override
    public PayType selectPayTypeById(Long id)
    {
        return payTypeMapper.selectPayTypeById(id);
    }

    /**
     * 查询支付方式列表
     *
     * @param payType 支付方式
     * @return 支付方式
     */
    @Override
    public List<PayType> selectPayTypeList(PayType payType)
    {
        return payTypeMapper.selectPayTypeList(payType);
    }

    /**
     * 新增支付方式
     *
     * @param payType 支付方式
     * @return 结果
     */
    @Override
    public int insertPayType(PayType payType)
    {
        payType.setCreateTime(DateUtils.getNowDate());
        return payTypeMapper.insertPayType(payType);
    }

    /**
     * 修改支付方式
     *
     * @param payType 支付方式
     * @return 结果
     */
    @Override
    public int updatePayType(PayType payType)
    {
        payType.setUpdateTime(DateUtils.getNowDate());
        return payTypeMapper.updatePayType(payType);
    }

    /**
     * 批量删除支付方式
     *
     * @param ids 需要删除的支付方式主键
     * @return 结果
     */
    @Override
    public int deletePayTypeByIds(Long[] ids)
    {
        return payTypeMapper.deletePayTypeByIds(ids);
    }

    /**
     * 删除支付方式信息
     *
     * @param id 支付方式主键
     * @return 结果
     */
    @Override
    public int deletePayTypeById(Long id)
    {
        return payTypeMapper.deletePayTypeById(id);
    }
}
