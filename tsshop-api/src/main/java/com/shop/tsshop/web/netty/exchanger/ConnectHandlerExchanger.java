package com.shop.tsshop.web.netty.exchanger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.tsshop.web.netty.protocol.command.MsgCommand;
import com.shop.tsshop.web.netty.protocol.packet.WsMessageRequestPacket;
import com.shop.tsshop.web.netty.session.Session;
import com.shop.tsshop.web.netty.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @Desc : ws连接成功
 * @Create : tsshop ~ 2020/06/14
 */
@Component
@Slf4j
public class ConnectHandlerExchanger implements HandlerExchanger {


    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    @Override
    public boolean support(Integer cmd) {
        return Objects.equals(MsgCommand.CONNECT.getCmd(), cmd);
    }

    @Override
    public void exchange(ChannelHandlerContext ctx, WsMessageRequestPacket packet) {


        // TODO 打开WS连接,保存Session
        // 当websocket 第一次open的时候，初始化channel，把用的channel和userid关联起来
        Session session=objectMapper.convertValue(packet.getUser(), Session.class);
        log.info(packet.getMessage());
        SessionUtil.bindSession(ctx.channel(), session);


        log.info("用户 | "+session.getName()+"  首次链接成功...");
    }
}
