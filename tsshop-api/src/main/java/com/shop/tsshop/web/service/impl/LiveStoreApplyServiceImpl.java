package com.shop.tsshop.web.service.impl;

import cn.hutool.core.lang.Validator;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.enums.NumberEnmus;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.mapper.LiveStoreApplyMapper;
import com.shop.tsshop.web.model.domain.LiveStoreApply;
import com.shop.tsshop.web.service.LiveStoreApplyService;
/**
 * @ClassName LiveStoreApplyServiceImpl
 * @Author TS SHOP
 * @create 2023/5/24
 */

@Service
@Slf4j
public class LiveStoreApplyServiceImpl extends ServiceImpl<LiveStoreApplyMapper, LiveStoreApply> implements LiveStoreApplyService{

    @Autowired
    LiveStoreApplyMapper liveStoreApplyMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public ApiResult<Object> applyLiveStore(LiveStoreApply liveStoreApply, HttpServletRequest request) {
        if (!Validator.isMobile(liveStoreApply.getManagerPhone())){
            return ApiResult.fail("请输入正确的手机号");
        }
        User user = redisService.getCurrentUser(request);
        liveStoreApply.setAuditStatus(NumberEnmus.ZERO.getNumber());
        liveStoreApply.setUserId(user.getId());
        liveStoreApplyMapper.insert(liveStoreApply);
        return ApiResult.ok();
    }
}
