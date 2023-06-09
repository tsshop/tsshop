package com.ts.shop.service.impl;


import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.PayThoroughfare;
import com.ts.shop.mapper.PayThoroughfareMapper;
import com.ts.shop.service.IPayThoroughfareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付通道Service业务层处理
 *
 * @author xu
 * @date 2023-04-25
 */
@Service
public class PayThoroughfareServiceImpl implements IPayThoroughfareService
{
    @Autowired
    private PayThoroughfareMapper payThoroughfareMapper;

    /**
     * 查询支付通道
     *
     * @param id 支付通道主键
     * @return 支付通道
     */
    @Override
    public PayThoroughfare selectPayThoroughfareById(Long id)
    {
        return payThoroughfareMapper.selectPayThoroughfareById(id);
    }

    /**
     * 查询支付通道列表
     *
     * @param payThoroughfare 支付通道
     * @return 支付通道
     */
    @Override
    public List<PayThoroughfare> selectPayThoroughfareList(PayThoroughfare payThoroughfare)
    {
        return payThoroughfareMapper.selectPayThoroughfareList(payThoroughfare);
    }

    /**
     * 新增支付通道
     *
     * @param payThoroughfare 支付通道
     * @return 结果
     */
    @Override
    public int insertPayThoroughfare(PayThoroughfare payThoroughfare)
    {
        payThoroughfare.setCreateTime(DateUtils.getNowDate());
        return payThoroughfareMapper.insertPayThoroughfare(payThoroughfare);
    }

    /**
     * 修改支付通道
     *
     * @param payThoroughfare 支付通道
     * @return 结果
     */
    @Override
    public int updatePayThoroughfare(PayThoroughfare payThoroughfare)
    {
        payThoroughfare.setUpdateTime(DateUtils.getNowDate());
        return payThoroughfareMapper.updatePayThoroughfare(payThoroughfare);
    }

    /**
     * 批量删除支付通道
     *
     * @param ids 需要删除的支付通道主键
     * @return 结果
     */
    @Override
    public int deletePayThoroughfareByIds(Long[] ids)
    {
        return payThoroughfareMapper.deletePayThoroughfareByIds(ids);
    }

    /**
     * 删除支付通道信息
     *
     * @param id 支付通道主键
     * @return 结果
     */
    @Override
    public int deletePayThoroughfareById(Long id)
    {
        return payThoroughfareMapper.deletePayThoroughfareById(id);
    }
}
