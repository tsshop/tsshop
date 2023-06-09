package com.shop.tsshop.web.controller;


import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.model.domain.Feedback;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.service.FeedbackService;
import com.shop.tsshop.web.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 反馈表 前端控制器
 * </p>
 *
 * @author xu
 * @since 2023-02-13
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;
    @Autowired
    private RedisService redisService;

    @PostMapping("/add")
    public ApiResult<Object> add(HttpServletRequest request, @RequestBody Feedback feedback) {
        User user = redisService.getCurrentUser(request);
        feedback.setUserId(user.getId());
        feedback.setStatus(1);
        return ApiResult.ok(feedbackService.save(feedback));
    }
}
