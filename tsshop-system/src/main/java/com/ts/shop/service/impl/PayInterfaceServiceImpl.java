package com.ts.shop.service.impl;

import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.PayInterface;
import com.ts.shop.mapper.PayInterfaceMapper;
import com.ts.shop.service.IPayInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付接口Service业务层处理
 *
 * @author xu
 * @date 2023-04-25
 */
@Service
public class PayInterfaceServiceImpl implements IPayInterfaceService
{
    @Autowired
    private PayInterfaceMapper payInterfaceMapper;

    /**
     * 查询支付接口
     *
     * @param id 支付接口主键
     * @return 支付接口
     */
    @Override
    public PayInterface selectPayInterfaceById(Long id)
    {
        return payInterfaceMapper.selectPayInterfaceById(id);
    }

    /**
     * 查询支付接口列表
     *
     * @param payInterface 支付接口
     * @return 支付接口
     */
    @Override
    public List<PayInterface> selectPayInterfaceList(PayInterface payInterface)
    {
        return payInterfaceMapper.selectPayInterfaceList(payInterface);
    }

    /**
     * 新增支付接口
     *
     * @param payInterface 支付接口
     * @return 结果
     */
    @Override
    public int insertPayInterface(PayInterface payInterface)
    {
        payInterface.setCreateTime(DateUtils.getNowDate());
        return payInterfaceMapper.insertPayInterface(payInterface);
    }

    /**
     * 修改支付接口
     *
     * @param payInterface 支付接口
     * @return 结果
     */
    @Override
    public int updatePayInterface(PayInterface payInterface)
    {
        payInterface.setUpdateTime(DateUtils.getNowDate());
        return payInterfaceMapper.updatePayInterface(payInterface);
    }

    /**
     * 批量删除支付接口
     *
     * @param ids 需要删除的支付接口主键
     * @return 结果
     */
    @Override
    public int deletePayInterfaceByIds(Long[] ids)
    {
        return payInterfaceMapper.deletePayInterfaceByIds(ids);
    }

    /**
     * 删除支付接口信息
     *
     * @param id 支付接口主键
     * @return 结果
     */
    @Override
    public int deletePayInterfaceById(Long id)
    {
        return payInterfaceMapper.deletePayInterfaceById(id);
    }

    @Override
    public Integer check(Long id) {
        return payInterfaceMapper.check(id);
    }
}
