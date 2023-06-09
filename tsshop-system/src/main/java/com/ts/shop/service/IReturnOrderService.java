package com.ts.shop.service;

import java.util.List;
import com.ts.shop.domain.ReturnOrder;
import com.ts.shop.domain.param.ReturnParam;

/**
 * 退款Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IReturnOrderService 
{
    /**
     * 查询退款
     * 
     * @param id 退款主键
     * @return 退款
     */
    public ReturnOrder selectReturnOrderById(Long id);

    /**
     * 查询退款列表
     * 
     * @param returnOrder 退款
     * @return 退款集合
     */
    public List<ReturnOrder> selectReturnOrderList(ReturnOrder returnOrder);

    /**
     * 新增退款
     * 
     * @param returnOrder 退款
     * @return 结果
     */
    public int insertReturnOrder(ReturnOrder returnOrder);

    /**
     * 修改退款
     * 
     * @param returnOrder 退款
     * @return 结果
     */
    public int updateReturnOrder(ReturnOrder returnOrder);

    /**
     * 批量删除退款
     * 
     * @param ids 需要删除的退款主键集合
     * @return 结果
     */
    public int deleteReturnOrderByIds(Long[] ids);

    /**
     * 删除退款信息
     * 
     * @param id 退款主键
     * @return 结果
     */
    public int deleteReturnOrderById(Long id);

    Object getGoods(Long id);

    void examine(ReturnParam param) throws Exception;

    Integer getMonthly(int i);
}
