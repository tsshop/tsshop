package com.shop.tsshop.web.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.enums.NumberEnmus;
import com.shop.tsshop.web.mapper.LiveStoreMapper;
import com.shop.tsshop.web.model.domain.LiveStore;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.LiveStoreIncomeCountDto;
import com.shop.tsshop.web.model.dto.LiveStoreIncomeListDto;
import com.shop.tsshop.web.model.vo.IncomeCountVo;
import com.shop.tsshop.web.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.mapper.LiveStoreIncomeLogMapper;
import com.shop.tsshop.web.model.domain.LiveStoreIncomeLog;
import com.shop.tsshop.web.service.LiveStoreIncomeLogService;
/**
 * @ClassName LiveStoreIncomeLogServiceImpl
 * @Author TS SHOP
 * @create 2023/5/23
 */

@Service
@Slf4j
public class LiveStoreIncomeLogServiceImpl extends ServiceImpl<LiveStoreIncomeLogMapper, LiveStoreIncomeLog> implements LiveStoreIncomeLogService{

    @Autowired
    LiveStoreIncomeLogMapper liveStoreIncomeLogMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    LiveStoreMapper liveStoreMapper;

    @Override
    public ApiResult<Object> liveStoreIncomeInfo(LiveStoreIncomeListDto dto, HttpServletRequest request) {
        LiveStore liveStore = getLiveStore(request);
        if (ObjectUtil.isNull(liveStore)){
            return ApiResult.fail("店铺信息有误，请刷新重试");
        }
        PageHelper.startPage(dto.getPageNumber(),dto.getPageSize());
        List<LiveStoreIncomeLog> liveStoreIncomeLogs = liveStoreIncomeLogMapper.selectList(new LambdaQueryWrapper<LiveStoreIncomeLog>()
                .eq(LiveStoreIncomeLog::getLiveStoreId, liveStore.getId())
                .eq(dto.getIncomeType() != null, LiveStoreIncomeLog::getIncomeType, dto.getIncomeType())
                .orderByDesc(LiveStoreIncomeLog::getCreateTime)
        );
        PageInfo<LiveStoreIncomeLog> pageInfo = new PageInfo<>(liveStoreIncomeLogs);
        return ApiResult.ok(pageInfo);
    }

    @Override
    public ApiResult<Object> liveStoreIncomeCount(LiveStoreIncomeCountDto dto, HttpServletRequest request) {
        LiveStore liveStore = getLiveStore(request);
        if (ObjectUtil.isNull(liveStore)){
            return ApiResult.fail("店铺信息异常，请刷新重试");
        }
        log.info("CourseArrangeList In Parameter [{}]",dto);
        BigDecimal giftIncome = new BigDecimal("0.00");
        BigDecimal orderIncome = new BigDecimal("0.00");
        IncomeCountVo incomeCountVo = new IncomeCountVo();
        List<LiveStoreIncomeLog> liveStoreIncomeLogs = new ArrayList<>();
        if (dto.getMonth() == 0){
            DateTime beginOfYear = DateUtil.beginOfYear(DateUtil.parse(dto.getYear().toString(), "yyyy"));
            DateTime endOfYear = DateUtil.endOfYear(DateUtil.parse(dto.getYear().toString(), "yyyy"));
            liveStoreIncomeLogs = liveStoreIncomeLogMapper.selectList(new LambdaQueryWrapper<LiveStoreIncomeLog>().eq(LiveStoreIncomeLog::getLiveStoreId, liveStore.getId()).between(LiveStoreIncomeLog::getCreateTime, beginOfYear, endOfYear));
        }else {
            DateTime monthStartTime = DateUtil.beginOfMonth(DateUtil.parse(dto.getYear() + "-" + dto.getMonth(), "yyyy-MM"));
            log.info("monthStartTime[{}]",monthStartTime);
            DateTime monthEndTime = DateUtil.endOfMonth(DateUtil.parse(dto.getYear() + "-" + dto.getMonth(), "yyyy-MM"));
            log.info("monthEndTime[{}]",monthEndTime);
            liveStoreIncomeLogs = liveStoreIncomeLogMapper.selectList(new LambdaQueryWrapper<LiveStoreIncomeLog>().eq(LiveStoreIncomeLog::getLiveStoreId, liveStore.getId()).between(LiveStoreIncomeLog::getCreateTime, monthStartTime, monthEndTime));
        }
        for (LiveStoreIncomeLog liveStoreIncomeLog : liveStoreIncomeLogs) {
            if (Objects.equals(liveStoreIncomeLog.getIncomeType(), NumberEnmus.ZERO.getNumber())){
                giftIncome = giftIncome.add(liveStoreIncomeLog.getAmt());
            }else {
                orderIncome = orderIncome.add(liveStoreIncomeLog.getAmt());
            }
        }
        incomeCountVo.setGiftIncome(giftIncome);
        incomeCountVo.setOrderIncome(orderIncome);
        incomeCountVo.setLiveStoreIncome(liveStore.getAmt());
        return ApiResult.ok(incomeCountVo);
    }

    @Override
    public void create(Long liveStoreId, BigDecimal amt, Integer incomeType) {
        LiveStoreIncomeLog liveStoreIncomeLog = new LiveStoreIncomeLog();
        liveStoreIncomeLog.setLiveStoreId(liveStoreId);
        liveStoreIncomeLog.setIncomeType(incomeType);
        liveStoreIncomeLog.setAmt(amt);
        save(liveStoreIncomeLog);
    }

    /**
     * 获取用户店铺信息
     * @param request          request
     * @return                 店铺信息
     */
    public LiveStore getLiveStore(HttpServletRequest request){
        User user = redisService.getCurrentUser(request);
        LiveStore liveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getUserId, user.getId()).eq(LiveStore::getDeleted, NumberEnmus.ZERO.getNumber()));
        if (ObjectUtil.isNull(liveStore)){
            return new LiveStore();
        }
        return liveStore;
    }
}
