package com.ts.shop.service;


import com.ts.shop.domain.PayInterface;

import java.util.List;

/**
 * 支付接口Service接口
 * 
 * @author xu
 * @date 2023-04-25
 */
public interface IPayInterfaceService 
{
    /**
     * 查询支付接口
     * 
     * @param id 支付接口主键
     * @return 支付接口
     */
    public PayInterface selectPayInterfaceById(Long id);

    /**
     * 查询支付接口列表
     * 
     * @param payInterface 支付接口
     * @return 支付接口集合
     */
    public List<PayInterface> selectPayInterfaceList(PayInterface payInterface);

    /**
     * 新增支付接口
     * 
     * @param payInterface 支付接口
     * @return 结果
     */
    public int insertPayInterface(PayInterface payInterface);

    /**
     * 修改支付接口
     * 
     * @param payInterface 支付接口
     * @return 结果
     */
    public int updatePayInterface(PayInterface payInterface);

    /**
     * 批量删除支付接口
     * 
     * @param ids 需要删除的支付接口主键集合
     * @return 结果
     */
    public int deletePayInterfaceByIds(Long[] ids);

    /**
     * 删除支付接口信息
     * 
     * @param id 支付接口主键
     * @return 结果
     */
    public int deletePayInterfaceById(Long id);

    Integer check(Long id);
}
