package com.shop.tsshop.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.LiveStoreWithdrawalsAccount;
import com.shop.tsshop.web.model.dto.CreateWithdrawalsAccountDto;
import com.shop.tsshop.web.model.dto.DelWithdrawalsAccountDto;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LiveStoreWithdrawalsAccountService
 * @Author TsShop
 * @create 2023/6/1
 */

public interface LiveStoreWithdrawalsAccountService extends IService<LiveStoreWithdrawalsAccount>{


    /**
     * 创建提现账户
     * @param createWithdrawalsAccountDto      dto
     * @param request                          request
     * @return                                 统一返回
     */
    ApiResult<Object> createWithdrawalsAccount(CreateWithdrawalsAccountDto createWithdrawalsAccountDto, HttpServletRequest request);

    /**
     * 获取用户的提现账户
     * @param request                           request
     * @param accountType                       账户类型
     * @return                                  统一返回
     */
    ApiResult<Object> getWithdrawalsAccount(String accountType,HttpServletRequest request);

    /**
     * 删除账户
     * @param delWithdrawalsAccountDto          dto
     * @param request                           request
     * @return                                  统一返回
     */
    ApiResult<Object> delWithdrawalsAccount(DelWithdrawalsAccountDto delWithdrawalsAccountDto, HttpServletRequest request);

    /**
     * 获取账户类型
     * @return                                  统一返回
     */
    ApiResult<Object> getAccountType();
}
