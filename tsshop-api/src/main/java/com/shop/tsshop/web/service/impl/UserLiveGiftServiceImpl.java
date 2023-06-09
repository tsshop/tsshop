package com.shop.tsshop.web.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.mapper.UserLiveGiftMapper;
import com.shop.tsshop.web.model.domain.UserLiveGift;
import com.shop.tsshop.web.service.UserLiveGiftService;
/**
 * @ClassName UserLiveGiftServiceImpl
 * @Author TS SHOP
 * @create 2023/5/23
 */

@Service
public class UserLiveGiftServiceImpl extends ServiceImpl<UserLiveGiftMapper, UserLiveGift> implements UserLiveGiftService{

    @Override
    public void deductionNumber(Long id, Long number) {
        baseMapper.deductionNumber(id, number);
    }
}
