package com.shop.tsshop.web.controller;

import cn.hutool.http.HttpUtil;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.mapper.OrderMapper;
import com.shop.tsshop.web.model.domain.Order;
import com.shop.tsshop.web.service.OrderService;
import com.shop.tsshop.web.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName PrecreateController
 * @Author TS SHOP
 * @create 2023/5/22
 */
@Slf4j
@RestController
@RequestMapping("/precreatePay")
public class PrecreateController {

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    OrderMapper orderMapper;

    @GetMapping("/getOrderStatus")
    public ApiResult getOrderStatus(@RequestParam("orderId") List<Long> orderId){
        List<Order> orders = orderMapper.selectBatchIds(orderId);
        Integer status = 1;
        for (Order order : orders) {
            if (order.getOrderStatus() == 0){
                status = 0;
            }
            log.info(">>>>>>>>>>>>>>>订单数据:" + order.toString());
        }
        return ApiResult.ok().ok(status);
    }

    @GetMapping("/link")
    public ApiResult<Object> link(String path,String query) {
        String token = null;
        String params = null;
        String wxAppId = redisService.getString("config:wei_xin_lite_appid");
        String wxAppSecret = redisService.getString("config:wei_xin_lite_appsecret");
        if (StringUtils.isEmpty(wxAppId) || StringUtils.isEmpty(wxAppSecret)){
            return ApiResult.fail("获取小程序配置信息失败，请联系管理员");
        }
        if(redisTemplate.hasKey("wx:AccessToken")){
            token = redisService.getObj("wx:AccessToken").toString();
        }else {
            params = "https://api.weixin.qq.com/cgi-bin/token";//网址;
            com.alibaba.fastjson.JSONObject req=new com.alibaba.fastjson.JSONObject();
            req.put("appid", wxAppId);
            req.put("secret", wxAppSecret);
            req.put("grant_type", "client_credential");
            com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(HttpUtil.get(params,req));
            token=json.getString("access_token");
            redisService.saveCode("wx:AccessToken",token, json.getLong("expires_in")-10);
        }

        params="https://api.weixin.qq.com/wxa/generate_urllink?access_token="+token;
        com.alibaba.fastjson.JSONObject req=new com.alibaba.fastjson.JSONObject();
        req.put("path", path);
        req.put("query", query);
        String result = HttpUtil.post(params,req.toString());
        System.out.println("result:"+result);
        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(result);
        if(!json.getString("errcode").equals("0")){
            return ApiResult.fail("系统繁忙，请稍后再试");
        }
        System.out.println(json);
        return ApiResult.ok(json.getString("url_link"));

    }
}
