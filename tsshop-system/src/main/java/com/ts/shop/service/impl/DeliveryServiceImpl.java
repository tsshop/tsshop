package com.ts.shop.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.mapper.DeliveryMapper;
import com.ts.shop.domain.Delivery;
import com.ts.shop.service.IDeliveryService;

/**
 * 物流公司Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class DeliveryServiceImpl implements IDeliveryService 
{
    @Autowired
    private DeliveryMapper deliveryMapper;

    /**
     * 查询物流公司
     * 
     * @param id 物流公司主键
     * @return 物流公司
     */
    @Override
    public Delivery selectDeliveryById(Long id)
    {
        return deliveryMapper.selectDeliveryById(id);
    }

    /**
     * 查询物流公司列表
     * 
     * @param delivery 物流公司
     * @return 物流公司
     */
    @Override
    public List<Delivery> selectDeliveryList(Delivery delivery)
    {
        return deliveryMapper.selectDeliveryList(delivery);
    }

    /**
     * 新增物流公司
     * 
     * @param delivery 物流公司
     * @return 结果
     */
    @Override
    public int insertDelivery(Delivery delivery)
    {
        return deliveryMapper.insertDelivery(delivery);
    }

    /**
     * 修改物流公司
     * 
     * @param delivery 物流公司
     * @return 结果
     */
    @Override
    public int updateDelivery(Delivery delivery)
    {
        return deliveryMapper.updateDelivery(delivery);
    }

    /**
     * 批量删除物流公司
     * 
     * @param ids 需要删除的物流公司主键
     * @return 结果
     */
    @Override
    public int deleteDeliveryByIds(Long[] ids)
    {
        return deliveryMapper.deleteDeliveryByIds(ids);
    }

    /**
     * 删除物流公司信息
     * 
     * @param id 物流公司主键
     * @return 结果
     */
    @Override
    public int deleteDeliveryById(Long id)
    {
        return deliveryMapper.deleteDeliveryById(id);
    }
}
