package com.shop.tsshop.web.controller;

import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.model.domain.Feedback;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.service.TsshopConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AgreementController
 * @Author TS SHOP
 * @create 2023/6/9
 */
@RestController
@RequestMapping("/agreement")
public class AgreementController {

    @Autowired
    TsshopConfigService tsshopConfigService;

    @GetMapping("/info")
    @PassToken
    public ApiResult<Object> info() {
        return tsshopConfigService.getAgreementInfo();
    }
}
