package com.ts.shop.service;

import com.ts.shop.domain.PayThoroughfare;

import java.util.List;

/**
 * 支付通道Service接口
 * 
 * @author xu
 * @date 2023-04-25
 */
public interface IPayThoroughfareService 
{
    /**
     * 查询支付通道
     * 
     * @param id 支付通道主键
     * @return 支付通道
     */
    public PayThoroughfare selectPayThoroughfareById(Long id);

    /**
     * 查询支付通道列表
     * 
     * @param payThoroughfare 支付通道
     * @return 支付通道集合
     */
    public List<PayThoroughfare> selectPayThoroughfareList(PayThoroughfare payThoroughfare);

    /**
     * 新增支付通道
     * 
     * @param payThoroughfare 支付通道
     * @return 结果
     */
    public int insertPayThoroughfare(PayThoroughfare payThoroughfare);

    /**
     * 修改支付通道
     * 
     * @param payThoroughfare 支付通道
     * @return 结果
     */
    public int updatePayThoroughfare(PayThoroughfare payThoroughfare);

    /**
     * 批量删除支付通道
     * 
     * @param ids 需要删除的支付通道主键集合
     * @return 结果
     */
    public int deletePayThoroughfareByIds(Long[] ids);

    /**
     * 删除支付通道信息
     * 
     * @param id 支付通道主键
     * @return 结果
     */
    public int deletePayThoroughfareById(Long id);
}
