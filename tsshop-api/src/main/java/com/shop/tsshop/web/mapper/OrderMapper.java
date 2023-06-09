package com.shop.tsshop.web.mapper;

import com.shop.tsshop.web.model.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.dto.LiveStoreOrderListDto;
import com.shop.tsshop.web.model.dto.OrderListDto;
import com.shop.tsshop.web.model.vo.LiveGiftBuyRecordVo;
import com.shop.tsshop.web.model.vo.OrderDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author xu
 * @since 2023-02-08
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<OrderDetailVo> getListByStatus(OrderListDto dto);

    List<OrderDetailVo>  selectListByLiveStoreId(@Param("dto") LiveStoreOrderListDto liveStoreOrderListDto, @Param("liveStoreId")Long id);

    List<LiveGiftBuyRecordVo> liveGiftBuyRecord( @Param("userId")Long id);
}
