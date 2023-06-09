package com.shop.tsshop.config.token;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.shop.tsshop.config.exception.MyException;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.service.RedisService;
import com.shop.tsshop.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author xu
 * token 拦截器
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
////
//    @Autowired
//    private RedisCache redisService;
//
//    @Autowired
//    private UserUtil userUtil;

    @Autowired
    private RedisService redisService;
    /**
     * 验证token是否正确
     *
     * @return
     * @throws Exception
     */
    
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 从 http 请求头中取出 token
        String token = httpServletRequest.getHeader("token");
        String bcEncrypt = httpServletRequest.getHeader("bcEncrypt");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 执行认证
        if (token == null || StringUtils.isEmpty(token)) {
            throw new MyException(401,"登录失效");
        }
        // 校验token 是否过期
        AccessToken accessToken = TokenUtil.verify(token);
        if (accessToken.getExpire()) {
            throw new MyException(401,"登录失效");
        }
        // 获取 token 中的 user account
        User user =redisService.getCurrentUser(httpServletRequest);
        if (user == null) {
            throw new MyException(401,"登录失效");
        }
//                if (user.getDeleted().equals(1)) {
//                    throw new MyException(ApiCode.NOT_PERMISSION.getCode(), ApiCode.LOGIN_USER_DELETE.getMsg());
//                }
        if (null == user.getToken() || user.getToken().isEmpty()) {
            throw new MyException(401,"登录失效");
        }

        if (!user.getToken().equals(token)) {
            throw new MyException(401,"登录失效");
        }
//        //设置用户信息
//        JwtUserInfo account = TokenUtil.getAccount(token);
//        UserHolder.setUserId(account);

//频繁操作的时间间隙
        int limit = 50;
        Long sec = 5L;
        String key = user.getId() + httpServletRequest.getRequestURI();

        Integer maxLimit = (Integer) redisService.getObj(key);
        if (maxLimit == null) {
            //set时一定要加过期时间
            redisService.saveCode(key, 1, sec);
        } else if (maxLimit < limit) {
            redisService.saveCode(key, maxLimit + 1, sec);
        } else {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("status", "406");
            jsonBody.put("msg", "请求太频繁!");
            output(httpServletResponse, String.valueOf(jsonBody));
            return false;
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        // log.info("销毁用户:"+UserHolder.getUserInfo().toString());
//        UserHolder.removeUserInfo();
    }

    public void output(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(msg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }
}
