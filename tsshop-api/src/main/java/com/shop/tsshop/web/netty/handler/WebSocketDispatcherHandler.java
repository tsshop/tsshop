package com.shop.tsshop.web.netty.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.tsshop.web.model.domain.User;
import com.shop.tsshop.web.netty.dispatcher.HandlerDispatcher;
import com.shop.tsshop.web.netty.protocol.packet.WsMessageRequestPacket;
import com.shop.tsshop.web.netty.session.Session;
import com.shop.tsshop.web.netty.utils.SessionUtil;
import com.shop.tsshop.web.netty.utils.SpringContextHolder;
import com.shop.tsshop.web.service.RedisService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc :
 * @Create : tsshop ~ 2020/06/13
 */
@Slf4j
@Component
public class WebSocketDispatcherHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static RedisService redisService;

    @Autowired
    public void setRedisService(RedisService redisService) {
        WebSocketDispatcherHandler.redisService = redisService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame text) throws Exception {
        log.info("ws handler ...");
        try {
            WsMessageRequestPacket wsMessage = objectMapper.readValue(text.text(), WsMessageRequestPacket.class);
            User user=redisService.getCurrentUser(wsMessage.getToken());
            wsMessage.setUser(user);
            text.retain();
            SpringContextHolder.getBean(HandlerDispatcher.class).dispatcher(ctx,wsMessage);
            /** test*/
            text.release();
        }
        finally {

        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("netty 获得连接 ...");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        log.info("客户端被移除，channelId为：" + channelId);
        // 移除Session
        Session session = SessionUtil.getSession(ctx.channel());
        SessionUtil.unbindSession(ctx.channel());
//        notifyOffline(session);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info(" netty 异常了...... ");
        cause.printStackTrace();
        // 移除Session
        Session session = SessionUtil.getSession(ctx.channel());
        SessionUtil.unbindSession(ctx.channel());
        // 发生异常之后关闭连接（关闭channel）
        ctx.channel().close();
        notifyOffline(session);
        log.info(" 关闭链接...... ");
    }

    private void notifyOffline(Session session){
        final AtomicInteger count = new AtomicInteger(0);
        log.info("{} 离线，通知给 {} 位好友",session.getName(),count.get());
    }

}
