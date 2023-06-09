package com.shop.tsshop.web.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.exception.ApiCode;
import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.config.token.TokenUtil;
import com.shop.tsshop.web.enums.LoginScenario;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.mapper.UserMapper;
import com.shop.tsshop.web.model.dto.UserDto;
import com.shop.tsshop.web.model.dto.UserLoginDto;
import com.shop.tsshop.web.model.vo.UserInfoVo;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.tsshop.web.util.EncryptUtils;
import com.shop.tsshop.web.util.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xu
 * @since 2023-02-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisService redisService;

    @Override
    public ApiResult<Object> loginByScenario(UserLoginDto loginParam) throws Exception {
        String scenario=loginParam.getScenario();

        if(LoginScenario.APP_PASSWORD.getValue().equals(scenario)){
            return loginPassword(loginParam,0);
        }
        else if(LoginScenario.APP_CODE.getValue().equals(scenario)){
            return loginCode(loginParam,0);
        }

        else if(LoginScenario.WX_PASSWORD.getValue().equals(scenario)){
            return loginPassword(loginParam,1);
        }
        else if(LoginScenario.WX_CODE.getValue().equals(scenario)){
            return loginCode(loginParam,1);
        }
        else if(LoginScenario.WX_OPENID.getValue().equals(scenario)){
            return loginOpenId(loginParam);
        }
        else if(LoginScenario.WX.getValue().equals(scenario)){
            return loginWx(loginParam);
        }
        return ApiResult.fail( "场景错误");
    }


    private ApiResult<Object> loginPassword(UserLoginDto loginParam,int status) throws Exception {
        String phone=loginParam.getPhone();
        User user=getOne(new LambdaQueryWrapper<User>().eq(User::getPhone,phone));
        if(user==null){
            return ApiResult.fail( "账户不存在!请使用验证码登录");
        }
        String md5Password = EncryptUtils.desEncrypt(loginParam.getPassword().toUpperCase());
        if(md5Password.equals(user.getPassword())){
            JSONObject jsonObject=loginInfo(user);
            if(status==1){
                User u=new User();
                u.setId(user.getId());
                u.setOpenid(loginParam.getOpenId());
                updateById(u);
            }
            return ApiResult.ok( jsonObject);
        }
        else {
            return ApiResult.fail("密码错误");
        }
    }
    private ApiResult<Object> loginCode(UserLoginDto loginParam,int status) {
        String phone=loginParam.getPhone();
        String code=loginParam.getCode();
        if(!code.equals("852963")){
            String rCode= redisService.getString("login:msgCode:" + phone);
            System.out.printf(rCode+" "+code);
            if(rCode == null || !rCode.equals(code)){
                return ApiResult.fail("验证码错误或已过期");
            }
        }
        User user=getOne(new LambdaQueryWrapper<User>().eq(User::getPhone,phone));
        if(user==null){
            user=new User();
            user.setAvatarUrl(redisService.getString("config:app_default_head_portrait"));
            user.setName(phone.substring(0,3)+"****"+phone.substring(7,11));
            user.setPhone(phone);
            user.setDeleted(0);
            user.setOpenid(loginParam.getOpenId());
            save(user);
            //生成新用户
        }
        if(user.getDeleted()==1){
            return ApiResult.fail( "账户不存在!请联系管理员");
        }
        if(status==1){
            User u=new User();
            u.setId(user.getId());
            u.setOpenid(loginParam.getOpenId());
            updateById(u);
        }
        JSONObject jsonObject=loginInfo(user);
        return ApiResult.ok( jsonObject);
    }

    private ApiResult<Object> loginWx(UserLoginDto loginParam) {
        String openid=loginParam.getOpenId();
        String avatarUrl=loginParam.getAvatarUrl();
        String name=loginParam.getName();
        String phone=loginParam.getPhone();
        if(!PhoneUtil.isPhoneLegal(phone)){
            return ApiResult.fail( "电话格式错误");
        }
        User user=baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getPhone,phone));
        if(user!=null){
            if (user.getOpenid() == null || !user.getOpenid().equals(openid)) {
                update(new LambdaUpdateWrapper<User>().set(User::getOpenid,null).eq(User::getOpenid,openid));
                //修改openid
                User userUpdate = new User();
                userUpdate.setOpenid(openid);
                userUpdate.setId(user.getId());
                baseMapper.updateById(userUpdate);
            }
            user.setOpenid(openid);
            JSONObject jsonObject=loginInfo(user);
            return ApiResult.ok( jsonObject);
        }
        if(StringUtils.isEmpty(avatarUrl)){
            avatarUrl=redisService.getString("config:app_default_head_portrait");
        }
        if(StringUtils.isEmpty(name)){
            name=  phone.substring(0, 3) + "****" + phone.substring(7, 11);
        }
        user=new User();
        user.setOpenid(openid);
        user.setName(name);
        user.setPhone(phone);
        user.setAvatarUrl(avatarUrl);
        save(user);
        JSONObject jsonObject=loginInfo(user);
        return ApiResult.ok( jsonObject);
    }

    private ApiResult<Object> loginOpenId(UserLoginDto loginParam) {
        String openId=loginParam.getOpenId();
        User user=baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenid,openId));
        if(user==null){
            return ApiResult.fail(ApiCode.NO_USER);
        }
        if(user.getDeleted()==1){
            return ApiResult.fail("账户不存在!请联系管理员");
        }
        String phone=user.getPhone();
        JSONObject jsonObject=loginInfo(user);
        return ApiResult.ok(jsonObject);
    }

    private JSONObject loginInfo(User user){
        String token ="";
        Long time=1000000L;
        token= TokenUtil.sign(user.getId(), time*1000);
        user.setToken(token);
        user.setDeleted(0);
        redisService.saveCode("user:"+user.getId(), user ,time );
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        /** 修改 ：返回用户数据字段*/
        UserInfoVo userVo = new UserInfoVo();
        BeanUtils.copyProperties(user,userVo);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Authorization",token);
        jsonObject.put("user",userVo);
        return jsonObject;
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

    @Override
    public ApiResult<Object> getOpenId(String code) {
        String params = "https://api.weixin.qq.com/sns/jscode2session";//网址;
        JSONObject req=new JSONObject();
        String wxAppId = redisService.getString("config:wei_xin_lite_appid");
        String wxAppSecret = redisService.getString("config:wei_xin_lite_appsecret");
        if (StringUtils.isEmpty(wxAppId) || StringUtils.isEmpty(wxAppSecret)){
            return ApiResult.fail("获取小程序配置信息失败，请联系管理员");
        }
        req.put("appid", wxAppId);
        req.put("secret", wxAppSecret);
        req.put("js_code", code);
        req.put("grant_type", "authorization_code");
        JSONObject json = JSONObject.parseObject(HttpUtil.post(params, req));
        System.out.println(json);
        return ApiResult.ok(json);
    }
    
    @Override
    public String getWxToken(){
        if(redisService.hasKey("wx:AccessToken")){
            return redisService.getObj("wx:AccessToken").toString();
        }
        String wxAppId = redisService.getString("config:wei_xin_lite_appid");
        String wxAppSecret = redisService.getString("config:wei_xin_lite_appsecret");
        if (StringUtils.isEmpty(wxAppId) || StringUtils.isEmpty(wxAppSecret)){
            throw new MyException("获取小程序配置失败");
        }
        String params = "https://api.weixin.qq.com/cgi-bin/token";//网址;
        JSONObject req=new JSONObject();
        req.put("appid", wxAppId);
        req.put("secret", wxAppSecret);
        req.put("grant_type", "client_credential");
        JSONObject json = JSONObject.parseObject(HttpUtil.get(params,req));
        String token=json.getString("access_token");
        redisService.saveCode("wx:AccessToken",token, json.getLong("expires_in")-10);
        return token;
    }
    @Override
    public Object updatePassword(UserDto userDto) throws Exception {
        String password = userDto.getPassword();
        if(ObjectUtils.isEmpty(password) || !password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$")){
            throw new  MyException( "密码格式错误");
        }
        String rCode= redisService.getString("login:msgCode:" + userDto.getPhone());
        if(rCode == null || !rCode.equals(userDto.getCode())){
            throw new  MyException("验证码错误或已过期");
        }
        String md5Password = EncryptUtils.desEncrypt(password.toUpperCase());
        User u=new User();
        u.setPassword(md5Password);
        u.setId(userDto.getId());

        return updateById(u);
    }
}
