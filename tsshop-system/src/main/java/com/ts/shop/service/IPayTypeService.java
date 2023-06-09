package com.ts.shop.service;

import com.ts.shop.domain.PayType;

import java.util.List;

/**
 * 支付方式Service接口
 *
 * @author xu
 * @date 2023-04-25
 */
public interface IPayTypeService
{
    /**
     * 查询支付方式
     *
     * @param id 支付方式主键
     * @return 支付方式
     */
    public PayType selectPayTypeById(Long id);

    /**
     * 查询支付方式列表
     *
     * @param payType 支付方式
     * @return 支付方式集合
     */
    public List<PayType> selectPayTypeList(PayType payType);

    /**
     * 新增支付方式
     *
     * @param payType 支付方式
     * @return 结果
     */
    public int insertPayType(PayType payType);

    /**
     * 修改支付方式
     *
     * @param payType 支付方式
     * @return 结果
     */
    public int updatePayType(PayType payType);

    /**
     * 批量删除支付方式
     *
     * @param ids 需要删除的支付方式主键集合
     * @return 结果
     */
    public int deletePayTypeByIds(Long[] ids);

    /**
     * 删除支付方式信息
     *
     * @param id 支付方式主键
     * @return 结果
     */
    public int deletePayTypeById(Long id);
}
