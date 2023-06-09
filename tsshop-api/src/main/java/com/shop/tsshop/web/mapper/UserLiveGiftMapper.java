package com.shop.tsshop.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.tsshop.web.model.domain.UserLiveGift;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @ClassName UserLiveGiftMapper
 * @Author TS SHOP
 * @create 2023/5/23
 */

public interface UserLiveGiftMapper extends BaseMapper<UserLiveGift> {

    @Update("update user_live_gift set live_gift_num = live_gift_num - #{number} where id = #{id}")
    void deductionNumber(@Param("id") Long id, @Param("number")Long number);
}