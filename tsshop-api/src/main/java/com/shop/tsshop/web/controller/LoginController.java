package com.shop.tsshop.web.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.exception.ApiCode;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.UserLoginDto;
import com.shop.tsshop.web.model.vo.UserInfoVo;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.service.UserService;
import com.shop.tsshop.web.service.UtilService;
import com.shop.tsshop.web.util.PhoneUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sunyawei
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private UtilService utilService;

    @PostMapping("/loginByScenario")
    @PassToken
    public ApiResult<Object> loginByScenario(@RequestBody UserLoginDto loginParam) throws Exception {
         return userService.loginByScenario(loginParam);
    }

    //"获取验证码"
    @PassToken
    @GetMapping(value = "/getCode")
    public ApiResult<Object> getCode(@RequestParam("phone") String phone
    ) throws ClientException {
        if (!PhoneUtil.isPhoneLegal(phone)) {
            return ApiResult.fail("请输入正确的手机号");
        }
        return utilService.sendMessage(phone,"login:");
    }

    @GetMapping("/userInfo")
    public ApiResult<Object> userInfo(HttpServletRequest request) {

//        User user = userService.getById(id);
        User user = redisService.getCurrentUser(request);

        if (ObjectUtils.isNotEmpty(user)) {

            UserInfoVo userDTO = mapperFacade.map(user, UserInfoVo.class);

            return ApiResult.ok(userDTO);
        }

        return ApiResult.result(ApiCode.UNAUTHORIZED.getCode(),ApiCode.UNAUTHORIZED.getMsg(), "");
    }


    @PostMapping("/out")
    // "退出登录"
    public ApiResult<Object> loginOut(HttpServletRequest request) {
        User currentUser = redisService.getCurrentUser(request);
        if (ObjectUtils.isNotEmpty(currentUser)) {
            redisService.delete("user:"+currentUser.getId());
        }
//        currentUser.setToken("");
//        redisService.saveUser(String.valueOf(currentUser.getId()), currentUser);
        return ApiResult.ok();
    }

    @GetMapping("/getOpenId")
    @PassToken
    public ApiResult<Object> getOpenId(String code) {
        return userService.getOpenId(code);
    }
    //五行 化龙  太乙 木 九转 玄清
    @GetMapping("/getPhone")
    @PassToken
    public ApiResult<Object> getPhone(String code){
        String token=userService.getWxToken();
        String params="https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token="+token;
        JSONObject req=new JSONObject();
        req.put("code", code);
        JSONObject json = JSONObject.parseObject(HttpUtil.post(params,req.toString()));
        if(!json.getString("errcode").equals("0")){
            return ApiResult.fail("系统繁忙，请稍后再试");
        }
        System.out.println(json);
        return ApiResult.ok(json.getJSONObject("phone_info").getString("phoneNumber"));
    }

    @GetMapping("/getWxToken")
    @PassToken
    public ApiResult<Object> getWxToken() {
        return ApiResult.ok(userService.getWxToken());
    }

}

