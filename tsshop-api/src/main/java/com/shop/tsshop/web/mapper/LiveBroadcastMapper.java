package com.shop.tsshop.web.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.domain.LiveBroadcast;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * @author : tsshop
 * @date : 2022-12-19
 */
public interface LiveBroadcastMapper extends BaseMapper<LiveBroadcast> {

    @Update(" update live_broadcast set praise_num = praise_num + #{number} where id = #{liveId} ")
    void praise(@Param("liveId") Long liveId,@Param("number") Integer number);

    @Update(" update live_broadcast set link_num = link_num + 1 where id = #{liveId} ")
    void addLinkNum(@Param("liveId") Long liveId);

    @Update(" update live_broadcast set order_profit = order_profit + #{profit} , order_num = order_num + 1 where id = #{liveId} ")
    void addProfit(@Param("liveId") Long liveId, @Param("profit") BigDecimal profit);
}
