package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.ReturnOrder;
import com.shop.tsshop.web.model.vo.ReturnOrderListVo;

import java.util.List;

/**
 * <p>
 * 退款表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-10
 */
public interface ReturnOrderService extends IService<ReturnOrder> {
    /**
     * 获取退款订单列表
     * @param id             id
     * @return               退款订单列表
     */
    List<ReturnOrderListVo> getList(Long id);
}
