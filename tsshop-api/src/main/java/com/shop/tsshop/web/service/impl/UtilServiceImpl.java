package com.shop.tsshop.web.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.enums.MessageConfigEnmu;
import com.shop.tsshop.web.model.domain.MessageConfig;
import com.shop.tsshop.web.model.domain.param.DayuConfigParams;
import com.shop.tsshop.web.model.domain.param.YunjiConfigPatams;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.service.UtilService;
import com.shop.tsshop.web.util.SmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UtilServiceImpl implements UtilService {


    @Autowired
    private RedisService redisService;
    @Override
    public ApiResult<Object> sendMessage(String phone, String folder) throws ClientException {
        Long code = Math.round((Math.random() + 1) * 100000);
        redisService.saveCode(folder+"timePhone:" + phone, 3, 10 * 60L);
        redisService.saveCode(folder+"msgCode:" + phone, code+"", 10 * 60L);
        String config = redisService.getString("config:message");
        if (StringUtils.isEmpty(config)){
            return ApiResult.fail("暂未配置短信服务，请联系管理员");
        }
        MessageConfig messageConfig = JSON.parseObject(config, MessageConfig.class);
        if (messageConfig.getType().equals(MessageConfigEnmu.YUNJI_OPEN_STATUS.getCode())){
            return yunjiSendMessage(phone,folder,code,messageConfig);
        }
        if (messageConfig.getType().equals(MessageConfigEnmu.ALI_DAYU_OPEN_STATUS.getCode())){
            return dayuSendMessage(phone,folder,code,messageConfig);
        }
        return ApiResult.fail("短信配置错误，请联系管理员");
    }
    /**
     * 阿里短信发送
     * @param phone                  手机号
     * @param folder                 缓存前缀
     * @param code                   验证码
     * @param messageConfig          短信配置
     * @return                       统一返回
     */
    private ApiResult<Object> dayuSendMessage(String phone,String folder,Long code, MessageConfig messageConfig) throws ClientException {
        DayuConfigParams dayuConfigParams = JSON.parseObject(messageConfig.getConfig(), DayuConfigParams.class);
        SendSmsResponse sendSmsResponse = SmsUtil.sendSms(phone, code + "", dayuConfigParams);
        if (!"OK".equals(sendSmsResponse.getCode())) {
            redisService.delete(folder + "msgCode:" + phone);
            redisService.delete(folder + "timePhone:" + phone);
            return ApiResult.fail(sendSmsResponse.getMessage());
        }
        return ApiResult.ok(sendSmsResponse.getMessage());
    }

    /**
     * 云极短信发送
     * @param phone                  手机号
     * @param folder                 缓存前缀
     * @param code                   验证码
     * @param messageConfig          短信配置
     * @return                       统一返回
     */
    private ApiResult<Object> yunjiSendMessage(String phone,String folder, Long code, MessageConfig messageConfig) {
        YunjiConfigPatams yunjiConfigPatams = JSON.parseObject(messageConfig.getConfig(), YunjiConfigPatams.class);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("accessKey", yunjiConfigPatams.getAccessKey());
        jsonBody.put("accessSecret", yunjiConfigPatams.getAccessSecret());
        jsonBody.put("signCode", yunjiConfigPatams.getSignCode());
        jsonBody.put("templateCode", yunjiConfigPatams.getTemplateCode());
        jsonBody.put("classificationSecret", yunjiConfigPatams.getClassificationSecret());
        jsonBody.put("phone", phone);
        String url = "https://market.juncdt.com/smartmarket/msgService/sendMessage";
        // 变量参数用map存
        Map<String, String> params = new HashMap<>();
        params.put("code", String.valueOf(code));
        // 变量参数map存入json对象
        jsonBody.put("params", params);
        JSONObject jsonObject = doPost(url, jsonBody);
        String businessData = String.valueOf(jsonObject.get("BusinessData"));
        if (ObjectUtils.isNotEmpty(businessData)) {
            JSONObject data = JSONObject.parseObject(businessData);
            String codes = String.valueOf(data.get("code"));
            if (StringUtils.isNotEmpty(codes)) {
                if ("10000".equals(codes)) {
                    return ApiResult.ok(data.get("msg"));
                } else {
                    redisService.delete(folder + "msgCode:" + phone);
                    redisService.delete(folder + "timePhone:" + phone);
                    return ApiResult.fail(String.valueOf(data.get("msg")));
                }
            }
            return ApiResult.ok("");
        }
        return ApiResult.fail("获取验证码失败，请稍后重试");
    }

    public static JSONObject doPost(String url, JSONObject json) {
        JSONObject response;
        try {
            String result = HttpUtil.post(url, json.toString());// 返回json格式
            response = JSONObject.parseObject(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
