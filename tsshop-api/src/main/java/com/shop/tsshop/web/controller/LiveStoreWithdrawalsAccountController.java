package com.shop.tsshop.web.controller;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.mapper.LiveStoreMapper;
import com.shop.tsshop.web.model.domain.LiveStore;
import com.shop.tsshop.web.model.domain.LiveStoreWithdrawalsAccount;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.CreateWithdrawalsAccountDto;
import com.shop.tsshop.web.model.dto.DelWithdrawalsAccountDto;
import com.shop.tsshop.web.model.dto.PageDto;
import com.shop.tsshop.web.service.LiveStoreWithdrawalsAccountService;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.service.UtilService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserWithdrawalsAccountController
 * @Author TS SHOP
 * @create 2023/6/1
 */
@RestController
@Slf4j
@RequestMapping("/liveStoreWithdrawalsAccount")
public class LiveStoreWithdrawalsAccountController {

    @Resource
    LiveStoreWithdrawalsAccountService liveStoreWithdrawalsAccountService;

    @Resource
    RedisService redisService;

    @Resource
    LiveStoreMapper liveStoreMapper;

    @Resource
    UtilService utilService;

    @GetMapping("/getCode")
    public ApiResult<Object> getVerificationCode(HttpServletRequest request) throws ClientException {
        User currentUser = redisService.getCurrentUser(request);
        LiveStore liveStore = liveStoreMapper.selectOne(new LambdaQueryWrapper<LiveStore>().eq(LiveStore::getUserId, currentUser.getId()));
        return utilService.sendMessage(liveStore.getStorePhone(),"tsshop:cashout:");
    }

    @GetMapping("/getAccount")
    public ApiResult<Object> getWithdrawalsAccount(String accountType,HttpServletRequest request) {
        return liveStoreWithdrawalsAccountService.getWithdrawalsAccount(accountType,request);
    }

    @PostMapping("/create")
    public ApiResult<Object> createWithdrawalsAccount(@RequestBody CreateWithdrawalsAccountDto createWithdrawalsAccountDto, HttpServletRequest request) {
        return liveStoreWithdrawalsAccountService.createWithdrawalsAccount(createWithdrawalsAccountDto,request);
    }

    @PostMapping("/del")
    public ApiResult<Object> delWithdrawalsAccount(@RequestBody DelWithdrawalsAccountDto dto, HttpServletRequest request) {
        return liveStoreWithdrawalsAccountService.delWithdrawalsAccount(dto,request);
    }

    @GetMapping("/accountType")
    public ApiResult<Object> getAccountType() {
        return liveStoreWithdrawalsAccountService.getAccountType();
    }


}
