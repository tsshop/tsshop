package com.shop.tsshop.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.web.enums.NumberEnmus;
import com.shop.tsshop.web.model.domain.LiveStoreApply;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.model.dto.GoodsSearchDto;
import com.shop.tsshop.web.model.vo.GoodsReviewVo;
import com.shop.tsshop.web.service.LiveStoreApplyService;
import com.shop.tsshop.web.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName LiveStoreApplyController
 * @Author TS SHOP
 * @create 2023/5/23
 */
@RestController
@Slf4j
@RequestMapping("/liveStoreApply")
public class LiveStoreApplyController {

    @Resource
    LiveStoreApplyService liveStoreApplyService;

    @PostMapping("/apply")
    public ApiResult<Object> applyLiveStore(@RequestBody LiveStoreApply liveStoreApply,HttpServletRequest request) {
        return liveStoreApplyService.applyLiveStore(liveStoreApply,request);
    }
}
