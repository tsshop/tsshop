package com.shop.tsshop.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.domain.LiveStore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

/**
 * @ClassName LiveStoreMapper
 * @Author TS SHOP
 * @create 2023/5/23
 */

public interface LiveStoreMapper extends BaseMapper<LiveStore> {

    @Select("update live_store set  amt = amt + #{profit} where id = #{liveStoreId}")
    void addProfit(@Param("liveStoreId") Long liveStoreId,@Param("profit") BigDecimal profit);
}