package com.shop.tsshop.web.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.tsshop.config.ApiResult;
import com.shop.tsshop.config.token.PassToken;
import com.shop.tsshop.web.enums.TencentEnums;
import com.shop.tsshop.web.enums.LiveEnums;
import com.shop.tsshop.web.model.domain.LiveBroadcast;
import com.shop.tsshop.web.model.dto.TencentCallbackDto;
import com.shop.tsshop.web.service.LiveBroadcastService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : tsshop
 * @date : 2022/12/20
 */
@Slf4j
@RestController
@RequestMapping("/tencentCloud")
public class TencentCloudController {

    @Autowired
    private LiveBroadcastService liveBroadcastService;

    /**
     * 事件回调
     *
     * @return
     */
    @PostMapping("/groupCallback")
    @PassToken
    public ApiResult groupCallback(@RequestBody String request) {
        //返回结果
        Map result = new HashMap();
        result.put("code", 0);
        log.info("腾讯云回调: {} ", request);
        TencentCallbackDto groupCallbackPo = JSONUtil.toBean(request, TencentCallbackDto.class);
        //直播回调处理
        liveCallback(groupCallbackPo);
        return ApiResult.ok(result);
    }

    /**
     * 直播回调处理
     *
     * @param groupCallbackPo
     */
    private void liveCallback(TencentCallbackDto groupCallbackPo) {
        try {
            if (groupCallbackPo.getEventGroupId().equals(1)) {
                LiveBroadcast liveBroadcast = liveBroadcastService.getOne(new LambdaQueryWrapper<LiveBroadcast>()
                        .eq(LiveBroadcast::getRoomId, Long.valueOf(groupCallbackPo.getEventInfo().getRoomId()))
                        .in(LiveBroadcast::getStatus, LiveEnums.LiveStatus.NOT_STARTED.getCode(), LiveEnums.LiveStatus.LIVE_BROADCAST.getCode()));
                if (ObjectUtil.isEmpty(liveBroadcast)){
                    return;
                }
                switch (TencentEnums.TencentCallbackEnum.getByCode(groupCallbackPo.getEventType())) {
                    //创建房间
                    case EVENT_TYPE_CREATE_ROOM:
                        liveBroadcastService.startLive(liveBroadcast, groupCallbackPo);
                        break;
                    //进入房间
                    case EVENT_TYPE_ENTER_ROOM:
                        liveBroadcastService.addRoom(liveBroadcast, groupCallbackPo);
                        break;
                    //退出房间
                    case EVENT_TYPE_EXIT_ROOM:
                        liveBroadcastService.outRoom(liveBroadcast, groupCallbackPo);
                        break;
                    //解散房间
                    case EVENT_TYPE_DISMISS_ROOM:
                        liveBroadcastService.endLive(liveBroadcast, groupCallbackPo);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
