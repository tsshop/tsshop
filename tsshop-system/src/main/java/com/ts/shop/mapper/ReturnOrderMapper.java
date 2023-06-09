package com.ts.shop.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.shop.domain.ReturnOrder;

/**
 * 退款Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface ReturnOrderMapper extends BaseMapper<ReturnOrder>
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
     * 删除退款
     * 
     * @param id 退款主键
     * @return 结果
     */
    public int deleteReturnOrderById(Long id);

    /**
     * 批量删除退款
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReturnOrderByIds(Long[] ids);

    Integer getMonthly(int status);
}
