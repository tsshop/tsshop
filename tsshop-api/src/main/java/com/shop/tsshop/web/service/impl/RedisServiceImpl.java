package com.shop.tsshop.web.service.impl;

import com.shop.tsshop.config.exception.ApiCode;
import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.config.token.JwtUserInfo;
import com.shop.tsshop.config.token.TokenUtil;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisServiceImpl implements RedisService {

    @Autowired
    public RedisTemplate redisTemplate;

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void flushdb() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Override
    public void saveUser(String key, Object user) {
        redisTemplate.opsForValue().set("user:" + key, user);
    }

    @Override
    public Object selectByUserId(String key) {
        return redisTemplate.opsForValue().get("user:"+key);
    }

    @Override
    public User getCurrentUser(HttpServletRequest httpServletRequest) {
        // 从 http 请求头中取出 token;
        String token = httpServletRequest.getHeader("token");
        JwtUserInfo account = TokenUtil.getAccount(token);
        Long userId =account.getUserId();
        User user =  (User) redisTemplate.opsForValue().get("user:" + userId);
        if (user == null) {
            throw new MyException(ApiCode.UNAUTHORIZED);
        }
        return user;
    }

    @Override
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public User getCurrentUser(String token) {
        JwtUserInfo account = TokenUtil.getAccount(token);
        Long userId =account.getUserId();
        User user =  (User) redisTemplate.opsForValue().get("user:" + userId);
        if (user == null) {
            throw new MyException(ApiCode.UNAUTHORIZED);
        }
        return user;
    }

    @Override
    public Object getObj(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    @Override
    public String getString(String key) {
        return Objects.requireNonNull(redisTemplate.opsForValue().get(key)).toString();
    }
    @Override
    public void saveCode(String key, Object val, Long time) {
        redisTemplate.opsForValue().set(key, val);
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }
    @Override
    public void saveCode(String key, Object val) {
        redisTemplate.opsForValue().set(key, val);
    }
}
