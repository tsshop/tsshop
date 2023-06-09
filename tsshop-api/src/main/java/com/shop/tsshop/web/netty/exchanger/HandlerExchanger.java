package com.shop.tsshop.web.netty.exchanger;

import cn.hutool.core.lang.Assert;
import com.shop.tsshop.web.netty.protocol.packet.WsMessageRequestPacket;
import com.shop.tsshop.web.netty.utils.ObjectMapperUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @Desc : ws消息处理交换器
 * @Create : tsshop ~ 2020/06/14
 */
public interface HandlerExchanger {

    boolean support(Integer cmd);

    void exchange(ChannelHandlerContext ctx, WsMessageRequestPacket packet);

    default void sendMessage(Channel channel, String message){
        channel.writeAndFlush(new TextWebSocketFrame(message).retain());
    }

    default <T> T readValue(WsMessageRequestPacket packet,Class<T> clazz, boolean checkNull){
        T t = ObjectMapperUtils.readValue(packet.getMessage(), clazz);
        if (checkNull) {
            Assert.notNull(t);
        }
        return t;
    }
    default <T> T readValue(WsMessageRequestPacket packet,Class<T> clazz){
        return readValue(packet, clazz, true);
    }
}
