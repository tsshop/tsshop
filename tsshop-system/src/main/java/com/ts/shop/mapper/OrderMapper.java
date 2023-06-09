package com.ts.shop.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.ts.shop.domain.Order;
import com.ts.shop.domain.OrderNumStatistics;
import com.ts.shop.domain.TurnoverStatistics;
import com.ts.shop.domain.dto.LiveGiftOrderListDto;
import com.ts.shop.domain.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 订单Mapper接口
 * 
 * @author ruoyi
 * @date 2023-02-14
 */
public interface OrderMapper extends BaseMapper<Order>
{
    /**
     * 查询订单
     * 
     * @param id 订单主键
     * @return 订单
     */
    public Order selectOrderById(Long id);

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
     * 删除订单
     * 
     * @param id 订单主键
     * @return 结果
     */
    public int deleteOrderById(Long id);

    /**
     * 批量删除订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderByIds(Long[] ids);

    Order selectByOrderNo(String orderNo);
    

    Integer getDailyOrder();

    Double getDailySales();

    List<MonthlySalesListVo> getMonthlySalesList();

    List<MonthlyOrderListVo> getMonthlyOrderList();

    Integer getMonthlyReturn();

    int getTotal(String payNo);

    List<RankingListVo> getAmtRanking(Integer status);

    List<RankingListVo> getNumRanking(Integer status);

    /**
     * 查询礼物订单
     * @param liveGiftOrderListDto            dto
     * @return                                LiveGiftOrderListVo
     */
    List<LiveGiftOrderListVo> liveGiftOrderList(@Param("dto") LiveGiftOrderListDto liveGiftOrderListDto);

    /**
     * 统计订单数量
     * @param between                         between
     * @return                                订单数量
     */
    @Select("SELECT COUNT( * ) " +
            "FROM `order`" +
            "${ew.customSqlSegment} \n")
    Long selectCountNum(@Param(Constants.WRAPPER) LambdaQueryWrapper<Order> between);

    /**
     * 统计销售数据
     * @param startTime                     开始时间
     * @param entTime                       结束时间
     * @return                              排行信息
     */
    List<SalesRankingVo> selectCountSalesRanking(@Param("startTime") String startTime,@Param("entTime")String entTime);

    /**
     * 统计周销售额
     * @param between                      时间范围
     * @return                             销售额
     */
    @Select("SELECT WEEKDAY(pay_time) + 1 AS unitNum,  \n" +
            "sum(total_amount) as count  \n" +
            "FROM `order`  \n" +
            "${ew.customSqlSegment} \n" +
            "GROUP BY\n" +
            "\tunitNum")
    List<TurnoverStatistics> weekCount(@Param(Constants.WRAPPER) LambdaQueryWrapper<Order> between);
    /**
     * 统计月销售额
     * @param between                      时间范围
     * @return                             销售额
     */
    @Select("SELECT DATE_FORMAT(pay_time, '%d' ) AS unitNum,  \n" +
            "sum(total_amount) as count  \n" +
            "FROM `order`  \n" +
            "${ew.customSqlSegment} \n" +
            "GROUP BY\n" +
            "\tunitNum")
    List<TurnoverStatistics> monthCount(@Param(Constants.WRAPPER) LambdaQueryWrapper<Order> between);
    /**
     * 统计年销售额
     * @param between                      时间范围
     * @return                             销售额
     */
    @Select("SELECT DATE_FORMAT(pay_time, '%m' ) AS unitNum,  \n" +
            "sum(total_amount) as count  \n" +
            "FROM `order`  \n" +
            "${ew.customSqlSegment} \n" +
            "GROUP BY\n" +
            "\tunitNum")
    List<TurnoverStatistics> yearCount(@Param(Constants.WRAPPER) LambdaQueryWrapper<Order> between);

    /**
     * 统计周订单数
     * @param between                      时间范围
     * @return                             订单数
     */
    @Select("SELECT WEEKDAY(create_time) + 1 AS unitNum,  \n" +
            "count(1) as count  \n" +
            "FROM `order`  \n" +
            "${ew.customSqlSegment} \n" +
            "GROUP BY\n" +
            "\tunitNum")
    List<OrderNumStatistics> weekOrderNumCount(@Param(Constants.WRAPPER) LambdaQueryWrapper<Order> between);

    /**
     * 统计月订单数
     * @param between                      时间范围
     * @return                             订单数
     */
    @Select("SELECT DATE_FORMAT(create_time, '%d' )  AS unitNum,  \n" +
            "count(1) as count  \n" +
            "FROM `order`  \n" +
            "${ew.customSqlSegment} \n" +
            "GROUP BY\n" +
            "\tunitNum")
    List<OrderNumStatistics> monthOrderNumCount(@Param(Constants.WRAPPER) LambdaQueryWrapper<Order> between);

    /**
     * 统计年订单数
     * @param between                      时间范围
     * @return                             订单数
     */
    @Select("SELECT DATE_FORMAT(create_time, '%m' ) + 1 AS unitNum,  \n" +
            "count(1) as count  \n" +
            "FROM `order`  \n" +
            "${ew.customSqlSegment} \n" +
            "GROUP BY\n" +
            "\tunitNum")
    List<OrderNumStatistics> yearOrderNumCount(@Param(Constants.WRAPPER) LambdaQueryWrapper<Order> between);

    /**
     * 统计订单累计金额
     * @return                             订单金额
     */
    @Select("SELECT " +
            "sum(total_amount)\n" +
            "FROM `order`  \n")
    BigDecimal countOrderMoney();
}

