package com.ts.shop.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ts.common.core.domain.AjaxResult;
import com.ts.common.utils.DateUtils;
import com.ts.shop.domain.*;
import com.ts.shop.domain.dto.LiveGiftOrderListDto;
import com.ts.shop.domain.vo.*;
import com.ts.shop.mapper.*;
import com.ts.shop.service.IAddressOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ts.shop.service.IOrderService;

import static cn.hutool.core.date.DateUtil.date;

/**
 * 订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements IOrderService
{
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ReturnOrderMapper returnOrderMapper;

    @Autowired
    private LiveStoreApplyMapper liveStoreApplyMapper;


    @Autowired
    private GoodsSkuMapper skuMapper;
    @Autowired
    private IAddressOrderService addressOrderService;
    /**
     * 查询订单
     * 
     * @param id 订单主键
     * @return 订单
     */
    @Override
    public Object selectOrderById(Long id)
    {
        JSONObject json=new JSONObject();
        Order order =orderMapper.selectOrderById(id);
        Goods goods=goodsMapper.selectGoodsById(order.getGoodsId());
        GoodsSku sku=skuMapper.selectGoodsSkuBySkuId(order.getSkuId());
        AddressVo addressVo=addressOrderService.selectAddresVoById(order.getAddressId());

        json.put("order",order);
        json.put("goods",goods);
        json.put("sku",sku);
        json.put("address",addressVo);
        return json;
    }

    /**
     * 查询订单列表
     * 
     * @param order 订单
     * @return 订单
     */
    @Override
    public List<Order> selectOrderList(Order order)
    {
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增订单
     * 
     * @param order 订单
     * @return 结果
     */
    @Override
    public int insertOrder(Order order)
    {
        order.setCreateTime(DateUtils.getNowDate());
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改订单
     * 
     * @param order 订单
     * @return 结果
     */
    @Override
    public int updateOrder(Order order)
    {
        order.setUpdateTime(DateUtils.getNowDate());
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除订单
     * 
     * @param ids 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderByIds(Long[] ids)
    {
        return orderMapper.deleteOrderByIds(ids);
    }

    /**
     * 删除订单信息
     * 
     * @param id 订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderById(Long id)
    {
        return orderMapper.deleteOrderById(id);
    }

    @Override
    public Double getDailySales() {
        return orderMapper.getDailySales();
    }

    @Override
    public Integer getDailyOrder() {
        return orderMapper.getDailyOrder();
    }

    @Override
    public List<MonthlySalesListVo> getMonthlySalesList() {
        return orderMapper.getMonthlySalesList();
    }

    @Override
    public List<MonthlyOrderListVo> getMonthlyOrderList() {
        return orderMapper.getMonthlyOrderList();
    }

    @Override
    public Integer getMonthlyReturn() {
        return orderMapper.getMonthlyReturn();
    }

    @Override
    public List<RankingListVo> getAmtRanking(Integer status) {
        return orderMapper.getAmtRanking(status);
    }

    @Override
    public List<RankingListVo> getNumRanking(Integer status) {
        return orderMapper.getNumRanking(status);
    }

    @Override
    public AjaxResult getLiveGiftOrderList(LiveGiftOrderListDto liveGiftOrderListDto) {
        PageHelper.startPage(liveGiftOrderListDto.getPageNumber(),liveGiftOrderListDto.getPageSize());
        List<LiveGiftOrderListVo> liveGiftOrderListVos = orderMapper.liveGiftOrderList(liveGiftOrderListDto);
        PageInfo<LiveGiftOrderListVo> pageInfo = new PageInfo<>(liveGiftOrderListVos);
        return AjaxResult.success(pageInfo);
    }

    @Override
    public AjaxResult countOrderStatus(Integer type) {
        if (!verifyType(type)){
            return AjaxResult.error("参数错误");
        }
        OrderStatusVo orderStatusVo = new OrderStatusVo();
        DateTime startTime = getStartTime(type);
        DateTime endTime = getEndTime(type);
        orderStatusVo.setFinishOrderNum(orderMapper.selectCountNum(new LambdaQueryWrapper<Order>().eq(Order::getOrderStatus,3).between(Order::getCreateTime,startTime,endTime)));
        orderStatusVo.setWaitDeliverOrderNum(orderMapper.selectCountNum(new LambdaQueryWrapper<Order>().eq(Order::getOrderStatus,1).between(Order::getCreateTime,startTime,endTime)));
        orderStatusVo.setWaitPayOrderNum(orderMapper.selectCountNum(new LambdaQueryWrapper<Order>().eq(Order::getOrderStatus,0).between(Order::getCreateTime,startTime,endTime)));
        orderStatusVo.setWaitReceivingOrderNum(orderMapper.selectCountNum(new LambdaQueryWrapper<Order>().eq(Order::getOrderStatus,2).between(Order::getCreateTime,startTime,endTime)));
        orderStatusVo.setCancelOrderNum(orderMapper.selectCountNum(new LambdaQueryWrapper<Order>().eq(Order::getOrderStatus,-1).between(Order::getCreateTime,startTime,endTime)));
        orderStatusVo.setOrderNum(orderMapper.selectCountNum(new LambdaQueryWrapper<Order>().between(Order::getCreateTime,startTime,endTime)));
        return AjaxResult.success(orderStatusVo);
    }



    @Override
    public AjaxResult countWaitMatter() {
        WaitMatterVo waitMatterVo = new WaitMatterVo();
        waitMatterVo.setReturnOrderNum(returnOrderMapper.selectCount(new LambdaQueryWrapper<ReturnOrder>().eq(ReturnOrder::getStatus,1)));
        waitMatterVo.setWaitDeliverOrderNum(orderMapper.selectCountNum(new LambdaQueryWrapper<Order>().eq(Order::getOrderStatus,1)));
        waitMatterVo.setWaitReceivingOrderNum(orderMapper.selectCountNum(new LambdaQueryWrapper<Order>().eq(Order::getOrderStatus,2)));
        waitMatterVo.setLiveStoreApplyNum(liveStoreApplyMapper.selectCount(new LambdaQueryWrapper<LiveStoreApply>().eq(LiveStoreApply::getAuditStatus,0)));
        return AjaxResult.success(waitMatterVo);
    }

    @Override
    public AjaxResult countSalesRanking(Integer type) {
        if (!verifyType(type)){
            return AjaxResult.error("参数错误");
        }
        DateTime startTime = getStartTime(type);
        DateTime endTime = getEndTime(type);
        List<SalesRankingVo> salesRankingVos = orderMapper.selectCountSalesRanking(DateUtil.formatDateTime(startTime),DateUtil.formatDateTime(endTime));
        salesRankingVos.forEach(vo -> vo.setSort(salesRankingVos.indexOf(vo) + 1));
        return AjaxResult.success(salesRankingVos);
    }

    @Override
    public AjaxResult countTurnover(Integer type) {
        if (!verifyType(type)){
            return AjaxResult.error("参数错误");
        }
        Map<String,Object> result = new HashMap<>();
        Integer length = 0;
        DateTime startTime = getStartTime(type);
        DateTime endTime = getEndTime(type);
        Map<Integer, BigDecimal> turnover= null;
        List<Integer> unitNumList = new ArrayList<>();
        List<BigDecimal> turnoverList = new ArrayList<>();
        switch (type){
            case 0:
                length = 7;
                turnover = Optional.ofNullable(orderMapper.weekCount(new LambdaQueryWrapper<Order>()
                                .between(Order::getPayTime,startTime,endTime))).orElseGet(ArrayList::new)
                        .stream().collect(Collectors.toMap(TurnoverStatistics::getUnitNum,TurnoverStatistics::getCount));
                for (int i = 1; i <= length; i++){
                    unitNumList.add(i);
                    turnoverList.add(ObjectUtil.isEmpty(turnover.get(i))? BigDecimal.valueOf(0) : turnover.get(i));
                }
                break;
            case 1:
                length = DateUtil.lengthOfMonth(DateUtil.date().month() + 1,DateUtil.isLeapYear(DateUtil.date().year()));
                turnover = Optional.ofNullable(orderMapper.monthCount(new LambdaQueryWrapper<Order>()
                                .between(Order::getPayTime,startTime,endTime))).orElseGet(ArrayList::new)
                        .stream().collect(Collectors.toMap(TurnoverStatistics::getUnitNum,TurnoverStatistics::getCount));
                for (int i = 1; i <= length; i++){
                    unitNumList.add(i);
                    turnoverList.add(ObjectUtil.isEmpty(turnover.get(i))? BigDecimal.valueOf(0) : turnover.get(i));
                }
                break;
            case 2:
                length = 12;
                turnover = Optional.ofNullable(orderMapper.yearCount(new LambdaQueryWrapper<Order>()
                                .between(Order::getPayTime,startTime,endTime))).orElseGet(ArrayList::new)
                        .stream().collect(Collectors.toMap(TurnoverStatistics::getUnitNum,TurnoverStatistics::getCount));
                for (int i = 1; i <= length; i++){
                    unitNumList.add(i);
                    turnoverList.add(ObjectUtil.isEmpty(turnover.get(i))? BigDecimal.valueOf(0) : turnover.get(i));
                }
                break;
        }
        result.put("turnoverList",turnoverList);
        result.put("unitNumList",unitNumList);
        return AjaxResult.success(result);
    }

    @Override
    public AjaxResult countOrderNum(Integer type) {
        if (!verifyType(type)){
            return AjaxResult.error("参数错误");
        }
        Map<String,Object> result = new HashMap<>();
        Integer length = 0;
        DateTime startTime = getStartTime(type);
        DateTime endTime = getEndTime(type);
        Map<Integer,Long> orderNum= null;
        List<Integer> unitNumList = new ArrayList<>();
        List<Long> orderNumList = new ArrayList<>();
        switch (type){
            case 0:
                length = 7;
                orderNum = Optional.ofNullable(orderMapper.weekOrderNumCount(new LambdaQueryWrapper<Order>()
                                .between(Order::getCreateTime,startTime,endTime))).orElseGet(ArrayList::new)
                        .stream().collect(Collectors.toMap(OrderNumStatistics::getUnitNum,OrderNumStatistics::getCount));
                for (int i = 1; i <= length; i++){
                    unitNumList.add(i);
                    orderNumList.add(ObjectUtil.isEmpty(orderNum.get(i))? 0 : orderNum.get(i));
                }
                break;
            case 1:
                length = DateUtil.lengthOfMonth(DateUtil.date().month() + 1,DateUtil.isLeapYear(DateUtil.date().year()));
                orderNum = Optional.ofNullable(orderMapper.monthOrderNumCount(new LambdaQueryWrapper<Order>()
                                .between(Order::getCreateTime,startTime,endTime))).orElseGet(ArrayList::new)
                        .stream().collect(Collectors.toMap(OrderNumStatistics::getUnitNum,OrderNumStatistics::getCount));
                for (int i = 1; i <= length; i++){
                    unitNumList.add(i);
                    orderNumList.add(ObjectUtil.isEmpty(orderNum.get(i))? 0 : orderNum.get(i));
                }
                break;
            case 2:
                length = 12;
                orderNum = Optional.ofNullable(orderMapper.yearOrderNumCount(new LambdaQueryWrapper<Order>()
                                .between(Order::getCreateTime,startTime,endTime))).orElseGet(ArrayList::new)
                        .stream().collect(Collectors.toMap(OrderNumStatistics::getUnitNum,OrderNumStatistics::getCount));
                for (int i = 1; i <= length; i++){
                    unitNumList.add(i);
                    orderNumList.add(ObjectUtil.isEmpty(orderNum.get(i))? 0 : orderNum.get(i));
                }
                break;
        }
        result.put("orderNumList",orderNumList);
        result.put("unitNumList",unitNumList);
        return AjaxResult.success(result);
    }

    @Override
    public AjaxResult getCountOrderInfo() {
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setFinishOrderNum(orderMapper.selectCountNum(new LambdaQueryWrapper<Order>().eq(Order::getOrderStatus,3)));
        orderInfoVo.setOrderMoney(orderMapper.countOrderMoney());
        QueryWrapper<ReturnOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("DISTINCT order_no");
        orderInfoVo.setReturnOrderNum(returnOrderMapper.selectCount(queryWrapper));
        orderInfoVo.setReturnOrderMoney(returnOrderMapper.selectList(new LambdaQueryWrapper<ReturnOrder>().eq(ReturnOrder::getStatus, 3).isNotNull(ReturnOrder::getReallyAmt))
                .stream().map(ReturnOrder::getReallyAmt)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        return AjaxResult.success(orderInfoVo);
    }

    /**
     * 校验时间范围入参
     * @param type                 时间范围 0 本周 1 当月 2 全年
     * @return                     结果
     */
    private Boolean verifyType(Integer type) {
        return (type == 0) || (type == 1) || (type == 2);
    }


    /**
     * 获取开始时间
     * @param type                  时间范围 0 本周 1 当月 2 全年
     * @return                      开始时间
     */
    private DateTime getStartTime(Integer type) {
        if (type == 0){
            return DateUtil.beginOfWeek(date());
        }else if (type == 1){
            return DateUtil.beginOfMonth(date());
        }else if (type == 2){
            return DateUtil.beginOfYear(date());
        }
        return null;
    }

    /**
     * 获取结束时间
     * @param type                  时间范围 0 本周 1 当月 2 全年
     * @return                      开始时间
     */
    private DateTime getEndTime(Integer type) {
        if (type == 0){
            return DateUtil.endOfWeek(date());
        }else if (type == 1){
            return DateUtil.endOfMonth(date());
        }else if (type == 2){
            return DateUtil.endOfYear(date());
        }
        return null;
    }
}
