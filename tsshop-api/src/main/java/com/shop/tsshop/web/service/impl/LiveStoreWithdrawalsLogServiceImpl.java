package com.shop.tsshop.web.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.enums.PayEnums;
import com.shop.tsshop.web.mapper.LiveStoreMapper;
import com.shop.tsshop.web.model.domain.LiveStore;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.PageDto;
import com.shop.tsshop.web.service.LiveStoreService;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.util.PayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.model.domain.LiveStoreWithdrawalsLog;
import com.shop.tsshop.web.mapper.LiveStoreWithdrawalsLogMapper;
import com.shop.tsshop.web.service.LiveStoreWithdrawalsLogService;
/**
 * @ClassName LiveStoreWithdrawalsLogServiceImpl
 * @Author TS SHOP
 * @create 2023/5/23
 */

@Service
public class LiveStoreWithdrawalsLogServiceImpl extends ServiceImpl<LiveStoreWithdrawalsLogMapper, LiveStoreWithdrawalsLog> implements LiveStoreWithdrawalsLogService{

    @Autowired
    LiveStoreWithdrawalsLogMapper liveStoreWithdrawalsLogMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    LiveStoreMapper liveStoreMapper;


    @Override
    public ApiResult<Object> getList(PageDto pageDto, HttpServletRequest request) {
        User currentUser = redisService.getCurrentUser(request);
        LiveStore liveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getDeleted, false).eq(LiveStore::getUserId, currentUser.getId()));
        if (ObjectUtil.isNull(liveStore)){
            return ApiResult.fail("店铺信息异常，请刷新重试");
        }
        PageHelper.startPage(pageDto.getPageNumber(),pageDto.getPageSize());
        List<LiveStoreWithdrawalsLog> liveStoreWithdrawalsLogs = liveStoreWithdrawalsLogMapper.selectList(new LambdaQueryWrapper<LiveStoreWithdrawalsLog>()
                .eq(LiveStoreWithdrawalsLog::getLiveStoreId, liveStore.getId())
                .orderByDesc(LiveStoreWithdrawalsLog::getCreateTime));
        liveStoreWithdrawalsLogs.forEach(item -> {
            if (item.getWithdrawalsType().equals(PayEnums.ProviderEnum.ALI_PAY.getCode()) || item.getWithdrawalsType().equals(PayEnums.PayTypeEnum.ALI_PAY_APP.getCode())){
                item.setWithdrawalsType("支付宝");
            }
        });
        PageInfo<LiveStoreWithdrawalsLog> pageInfo = new PageInfo(liveStoreWithdrawalsLogs);
        return ApiResult.ok(pageInfo);
    }
}
