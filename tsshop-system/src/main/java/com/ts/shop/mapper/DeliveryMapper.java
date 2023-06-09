package com.ts.shop.mapper;

import java.util.List;
import com.ts.shop.domain.Delivery;

/**
 * 物流公司Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface DeliveryMapper 
{
    /**
     * 查询物流公司
     * 
     * @param id 物流公司主键
     * @return 物流公司
     */
    public Delivery selectDeliveryById(Long id);

    /**
     * 查询物流公司列表
     * 
     * @param delivery 物流公司
     * @return 物流公司集合
     */
    public List<Delivery> selectDeliveryList(Delivery delivery);

    /**
     * 新增物流公司
     * 
     * @param delivery 物流公司
     * @return 结果
     */
    public int insertDelivery(Delivery delivery);

    /**
     * 修改物流公司
     * 
     * @param delivery 物流公司
     * @return 结果
     */
    public int updateDelivery(Delivery delivery);

    /**
     * 删除物流公司
     * 
     * @param id 物流公司主键
     * @return 结果
     */
    public int deleteDeliveryById(Long id);

    /**
     * 批量删除物流公司
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeliveryByIds(Long[] ids);
}
