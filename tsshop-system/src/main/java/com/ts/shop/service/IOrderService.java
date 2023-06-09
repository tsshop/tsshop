package com.ts.shop.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ts.common.core.domain.AjaxResult;
import com.ts.shop.domain.Order;
import com.ts.shop.domain.dto.LiveGiftOrderListDto;
import com.ts.shop.domain.vo.MonthlyOrderListVo;
import com.ts.shop.domain.vo.MonthlySalesListVo;
import com.ts.shop.domain.vo.RankingListVo;

/**
 * 订单Service接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IOrderService extends IService<Order>
{
    /**
     * 查询订单
     * 
     * @param id 订单主键
     * @return 订单
     */
    public Object selectOrderById(Long id);

    /**
     * 查询订单列表
     * 
     * @param order 订单
     * @return 订单集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增订单
     * 
     * @param order 订单
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改订单
     * 
     * @param order 订单
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     *                   批量删除订单
     * 
     * @param ids        需要删除的订单主键集合
     * @return           结果
     */
    public int deleteOrderByIds(Long[] ids);

    /**
     * 删除订单信息
     * 
     * @param id        订单主键
     * @return          结果
     */
    public int deleteOrderById(Long id);

    /**
     * 获取今日销售额
     * @return          销售额
     */
    Double getDailySales();

    Integer getDailyOrder();

    List<MonthlySalesListVo> getMonthlySalesList();

    List<MonthlyOrderListVo> getMonthlyOrderList();

    Integer getMonthlyReturn();

    List<RankingListVo> getAmtRanking(Integer status);

    List<RankingListVo> getNumRanking(Integer status);

    /**
     * 查询礼物订单列表
     * @param liveGiftOrderListDto               dto
     * @return                                   统一返回
     */
    AjaxResult getLiveGiftOrderList(LiveGiftOrderListDto liveGiftOrderListDto);

    /**
     * 统计订单情况
     * @param type                               时间范围 0 本周 1 当月 2 全年
     * @return                                   统一返回
     */
    AjaxResult countOrderStatus(Integer type);

    /**
     * 统计待办事项条目
     * @return                                   统一返回
     */
    AjaxResult countWaitMatter();

    /**
     * 统计销量排行
     * @param type                               时间范围 0 本周 1 当月 2 全年
     * @return                                   统一返回
     */
    AjaxResult countSalesRanking(Integer type);

    /**
     * 统计成交额
     * @param type                               时间范围 0 本周 1 当月 2 全年
     * @return                                   统一返回
     */
    AjaxResult countTurnover(Integer type);

    /**
     * 统计订单数（折线图）
     * @param type                               时间范围 0 本周 1 当月 2 全年
     * @return                                   统一返回
     */
    AjaxResult countOrderNum(Integer type);

    /**
     * 统计订单信息
     * @return                                   统一返回
     */
    AjaxResult getCountOrderInfo();
}
