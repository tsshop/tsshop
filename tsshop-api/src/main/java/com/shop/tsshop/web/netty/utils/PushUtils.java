package com.shop.tsshop.web.netty.utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

/**
 * @author : tsshop
 * @date : 2022/12/12
 */
@Slf4j
public class PushUtils {

    /**
     * 单人发送指令
     * @param userId
     * @param respMsg
     */
    public static void push(Long userId, String respMsg) {
        Channel channel = SessionUtil.getChannel(userId.toString());
        if (null != channel) {
            log.info("接收用户："+ userId + "内容：" + respMsg);
            channel.writeAndFlush(new TextWebSocketFrame(respMsg).retain());
        }
    }

    /**
     * 多人发送指令
     * @param userIds
     * @param respMsg
     */
    public static void push(List<String> userIds, String respMsg) {
        userIds.forEach(item -> {
            Channel channel = SessionUtil.getChannel(item);
            if (null != channel) {
                log.info("接收用户："+ item + "内容：" + respMsg);
                channel.writeAndFlush(new TextWebSocketFrame(respMsg).retain());
            }
        });
    }

    /**
     * 多人发送指令
     * @param userIds
     * @param respMsg
     */
    public static void push(Set<String> userIds, String respMsg) {
        userIds.forEach(item -> {
            Channel channel = SessionUtil.getChannel(item);
            if (null != channel) {
                log.info("接收用户："+ item + "内容：" + respMsg);
                channel.writeAndFlush(new TextWebSocketFrame(respMsg).retain());
            }
        });
    }

    public static void pushOut(ChannelHandlerContext ctx){
        Channel channel = ctx.channel();
        channel.writeAndFlush(new TextWebSocketFrame("{\"cmd\":401}").retain());
    }

}
