package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.web.model.domain.UserLiveGift;
    /**
 * @ClassName UserLiveGiftService
 * @Author TsShop
 * @create 2023/5/23
 */

public interface UserLiveGiftService extends IService<UserLiveGift>{


        void deductionNumber(Long id, Long number);
    }
