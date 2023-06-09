package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.Order;
import com.shop.tsshop.web.model.dto.OrderDto;
import com.shop.tsshop.web.model.dto.OrderListDto;
import com.shop.tsshop.web.model.vo.OrderDetailVo;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author xu
 * @since 2023-02-07
 */
public interface OrderService extends IService<Order> {


    /**
     * 创建订单
     * @param orderDto                dto
     * @return                        obj
     */
    Object createOrder(OrderDto orderDto);

    /**
     * 统计金额
     * @param orderDto                dto
     * @return                        obj
     */
    Object allMoney(OrderDto orderDto);

    /**
     * 获取订单详情
     * @param order                   order
     * @return                        订单详情
     */
    OrderDetailVo getDetail(Order order);

    /**
     * 获取订单详情
     * @param dto                     dto
     * @return                        订单详情列表
     */
    List<OrderDetailVo> getListByStatus(OrderListDto dto);

    /**
     * 取消订单
     * @param order                   订单信息
     */
    void cancel(Order order);

    /**
     * 生成礼物订单
     * @param orderDto           dto
     * @return                   统一返回
     */
    Object createGiftOrder(OrderDto orderDto);

    /**
     * 创建直播订单
     * @param orderDto           直播订单
     * @return                   obj
     */
    Object createLiveOrder(OrderDto orderDto);

    /**
     * 统计订单金额
     * @param orderDto           dto
     * @return                   统一返回
     */
    Object liveAllMoney(OrderDto orderDto);

    /**
     * 直播订单收益
     * @param order              订单
     */
    void liveOrderProfit(Order order);
}
