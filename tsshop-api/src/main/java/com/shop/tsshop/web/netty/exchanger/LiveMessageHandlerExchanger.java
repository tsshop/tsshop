package com.shop.tsshop.web.netty.exchanger;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shop.tsshop.web.enums.LiveEnums;
import com.shop.tsshop.web.model.domain.LiveBroadcast;
import com.shop.tsshop.web.model.domain.LiveMessage;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.netty.protocol.command.MsgCommand;
import com.shop.tsshop.web.netty.protocol.packet.WsMessageRequestPacket;
import com.shop.tsshop.web.netty.protocol.request.LiveMessageBody;
import com.shop.tsshop.web.netty.utils.ObjectMapperUtils;
import com.shop.tsshop.web.netty.utils.PushUtils;
import com.shop.tsshop.web.service.LiveBroadcastService;
import com.shop.tsshop.web.service.LiveMessageService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author : tsshop
 * @date : 2023/5/24
 */
@Component
@Slf4j
public class LiveMessageHandlerExchanger implements HandlerExchanger{

    @Autowired
    private LiveBroadcastService liveBroadcastService;

    @Autowired
    private LiveMessageService liveMessageService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean support(Integer cmd) {
        return Objects.equals(MsgCommand.LIVE_MESSAGE.getCmd(), cmd);
    }

    @Override
    public void exchange(ChannelHandlerContext ctx, WsMessageRequestPacket packet) {
        LiveMessageBody messageBody = ObjectMapperUtils.readValue(packet.getMessage(), LiveMessageBody.class);
        User user = packet.getUser();
        Assert.notNull(messageBody);
        LiveBroadcast liveBroadcast = liveBroadcastService.getOne(new LambdaQueryWrapper<LiveBroadcast>()
                .eq(LiveBroadcast::getRoomId, messageBody.getRoomId())
                .eq(LiveBroadcast::getStatus, LiveEnums.LiveStatus.LIVE_BROADCAST.getCode()));
        if (ObjectUtil.isEmpty(liveBroadcast)){
            return;
        }
        LiveMessage message = new LiveMessage()
                .setLiveId(liveBroadcast.getId())
                .setContent(messageBody.getContent())
                .setUserId(user.getId());
        liveMessageService.save(message);

        String onlineKey = "live_online_user:" + liveBroadcast.getRoomId();
        Set<String> userIds = redisTemplate.opsForHash().keys(onlineKey);
        userIds.add(liveBroadcast.getUserId().toString());
        //发送指令
        Map msg = new HashMap<>();
        msg.put("cmd",MsgCommand.LIVE_MESSAGE.getCmd());
        msg.put("roomId",messageBody.getRoomId());
        msg.put("userName",user.getName());
        msg.put("content",messageBody.getContent());
        PushUtils.push(userIds, JSONUtil.toJsonStr(msg));
    }
}
