package com.shop.tsshop.web.netty.exchanger;

import com.shop.tsshop.web.netty.protocol.command.MsgCommand;
import com.shop.tsshop.web.netty.protocol.packet.WsMessageRequestPacket;
import com.shop.tsshop.web.netty.protocol.request.Mine;
import com.shop.tsshop.web.netty.session.Session;
import com.shop.tsshop.web.netty.utils.ObjectMapperUtils;
import com.shop.tsshop.web.netty.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Desc : 心跳
 * @Create : tsshop ~ 2020/06/14
 */
@Component
@Slf4j
public class KeepAliveHandlerExchanger implements HandlerExchanger {

    @Override
    public boolean support(Integer cmd) {
        return Objects.equals(MsgCommand.KEEPALIVE.getCmd(), cmd);
    }

    @Override
    public void exchange(ChannelHandlerContext ctx, WsMessageRequestPacket packet) {
        // TODO 心跳消息
        Mine mine = ObjectMapperUtils.readValue(packet.getMessage(), Mine.class);
        log.info("心跳消息: ["+packet.getUser().getName()+"]");
        if(mine == null){
            log.info("心跳包不包含用户信息");
            return;
        }
        Channel fromChannel = SessionUtil.getChannel(mine.getId());
        if (null == fromChannel){
            log.info("短线重连导致获取到channel，重新绑定");
            SessionUtil.bindSession(ctx.channel(), Objects.requireNonNull(ObjectMapperUtils.readValue(packet.getMessage(), Session.class)));
        }
    }
}
